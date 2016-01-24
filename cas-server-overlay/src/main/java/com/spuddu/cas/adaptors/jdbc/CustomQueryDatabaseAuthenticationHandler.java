package com.spuddu.cas.adaptors.jdbc;

import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.spuddu.cas.authentication.handler.SpringPasswordEncoderEquipped;

/**
 * A JDBC querying handler, like QueryDatabaseAuthenticationHandler, that checks the password throught 
 * the spring password encoder injected into the current passwordEncoder (if present)
 * 
 * <p>
 * To use in conjunction with {@link SpringPasswordEncoderEquipped} password encoders
 * </p> 
 */
public class CustomQueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@NotNull
	private String sql;


	/**
	 * TODO - caddozzo
	 * @return if present, returns the spring encoder used by the current password encoder
	 */
	private org.springframework.security.crypto.password.PasswordEncoder getSpringEncoder() {
		logger.debug("Checking if the current password encoder {} is using a spring encoder", getPasswordEncoder().getClass().getName());
		if (this.getPasswordEncoder() instanceof SpringPasswordEncoderEquipped) {
			org.springframework.security.crypto.password.PasswordEncoder springEnc = ((SpringPasswordEncoderEquipped)this.getPasswordEncoder()).getSpringPasswordEncoderImplementation();
			logger.debug("The current password encoder {} is using springs {}", getPasswordEncoder().getClass().getName(), springEnc.getClass().getName());
			return springEnc;
		}
		logger.debug("The current password encoder {} do not use spring", getPasswordEncoder().getClass().getName());
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	 @Override
	 protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
			 throws GeneralSecurityException, PreventedException {

		 final String username = credential.getUsername();
		 try {
			 final String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username);
			 
			 org.springframework.security.crypto.password.PasswordEncoder springEncoder = this.getSpringEncoder();
			 if (null != springEncoder) {
				 logger.debug("Checking password with {}", springEncoder.getClass().getName());				 
				 if (!springEncoder.matches(credential.getPassword(), dbPassword)) {
					 throw new FailedLoginException("Password does not match:" + dbPassword);
				 }				 
			 } else {
				 final String encryptedPassword = this.getPasswordEncoder().encode(credential.getPassword());
				 if (!dbPassword.equals(encryptedPassword)) {
					 throw new FailedLoginException("Password does not match value on record.");
				 }
			 }
		 } catch (final IncorrectResultSizeDataAccessException e) {
			 if (e.getActualSize() == 0) {
				 throw new AccountNotFoundException(username + " not found with SQL query");
			 } else {
				 throw new FailedLoginException("Multiple records found for " + username);
			 }
		 } catch (final DataAccessException e) {
			 throw new PreventedException("SQL exception while executing query for " + username, e);
		 }
		 return createHandlerResult(credential, this.principalFactory.createPrincipal(username), null);
	 }

	 /**
	  * @param sql The sql to set.
	  */
	 public void setSql(final String sql) {
		 this.sql = sql;
	 }
}