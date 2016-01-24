package com.spuddu.cas.authentication.handler;

import org.jasig.cas.authentication.handler.PasswordEncoder;

/**
 * Custom CAS {@link PasswordEncoder} using
 * a standard spring {@link org.springframework.security.crypto.password.PasswordEncoder} to do his job
 *
 */
public class SpringPasswordEncoder implements PasswordEncoder, SpringPasswordEncoderEquipped {


	private org.springframework.security.crypto.password.PasswordEncoder springPasswordEncoderImplementation;

	@Override
	public String encode(String password) {
		if (password == null) {
			return null;
		}
		String pass = this.getSpringPasswordEncoderImplementation().encode(password);
		return pass;
	}

	public org.springframework.security.crypto.password.PasswordEncoder getSpringPasswordEncoderImplementation() {
		return springPasswordEncoderImplementation;
	}

	public void setSpringPasswordEncoderImplementation(
			org.springframework.security.crypto.password.PasswordEncoder springPasswordEncoderImplementation) {
		this.springPasswordEncoderImplementation = springPasswordEncoderImplementation;
	}

}
