package com.scripteasy.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.scripteasy.DTO.UserNewDTO;
import com.scripteasy.domain.UserSE;
import com.scripteasy.repositories.UserRepository;
import com.scripteasy.resources.exception.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

	@Autowired
	private UserRepository repo;

	@Override
	public void initialize(UserInsert ann) {
	}

	@Override
	public boolean isValid(UserNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();


		UserSE aux = repo.findByEmail(objDto.getEmail());
		if(aux !=null) {
			list.add(new FieldMessage("email", "existing email"));
		}
		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}