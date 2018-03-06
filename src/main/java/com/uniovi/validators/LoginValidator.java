package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
@Component
public class LoginValidator implements Validator{

	@Autowired
	private UsersService usersService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}
	//No funciona correctamente
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		
		
		if (usersService.getUserByEmail(user.getEmail()) == null) {
			errors.rejectValue("email", "Error.login.email.incorrect");
		}
		if(!usersService.getUserByEmail(user.getEmail()).getPassword().equals(user.getPassword())){
			errors.rejectValue("password", "Error.login.password.incorrect");
		}
	
		
	}

}
