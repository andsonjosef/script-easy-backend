package com.scripteasy.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.DTO.UserDTO;
import com.scripteasy.DTO.UserNewDTO;
import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.UserRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.DataIntegrityException;
import com.scripteasy.services.excpetion.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	public UserSE find(Integer id) {
		UserSS user = UserSService.authenticated();

		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}

		Optional<UserSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + UserSE.class.getName()));
	}

	@Transactional
	public UserSE insert(UserSE obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public UserSE update(UserSE obj) {
		UserSE newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete because there are related orders");
		}
	}

	public List<UserSE> findAll() {
		return repo.findAll();
	}

	public Page<UserSE> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public UserSE fromDTO(UserDTO objDto) {
		return new UserSE(objDto.getId(), objDto.getName(), objDto.getEmail(), null);

	}

	public UserSE fromDTO(UserNewDTO objDto) {
		UserSE user = new UserSE(null, objDto.getName(), objDto.getEmail(),
				pe.encode(objDto.getPassword()));

		
		return user;

	}

	private void updateData(UserSE newObj, UserSE obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());

	}

}