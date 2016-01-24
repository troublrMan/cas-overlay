package com.spuddu.cas.authentication.handler;

import org.jasig.cas.authentication.handler.PasswordEncoder;

/**
 * Interface meant to mark any custom jasig {@link PasswordEncoder} that performs encoding by using a standard Spring PasswordEncoder 
 *
 */
public interface SpringPasswordEncoderEquipped {

	/**
	 * The specific spring password encoder
	 * @return The specific spring password encoder
	 */
	public org.springframework.security.crypto.password.PasswordEncoder getSpringPasswordEncoderImplementation();

}
