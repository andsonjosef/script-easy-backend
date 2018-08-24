package com.scripteasy.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.DTO.DataBaseDTO;
import com.scripteasy.DTO.DataBaseNewDTO;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.DataBaseRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.DataIntegrityException;
import com.scripteasy.services.excpetion.ObjectNotFoundException;



@Service
public class DataBaseService {


	@Autowired
	private DataBaseRepository repo;


	@Autowired
	UserService userService;

	public DataBaseSE find(Integer id) {

		Optional<DataBaseSE> obj = repo.findById(id);
		
		UserSS user = UserSService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !obj.get().getUser().getId().equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + DataBaseSE.class.getName()));
	}

	@Transactional
	public DataBaseSE insert(DataBaseSE obj) {
		obj.setId(null);
		obj.setUser(userService.find(obj.getUser().getId()));
		obj = repo.save(obj);

		return obj;
	}
	

	public DataBaseSE update(DataBaseSE obj) {
		
		Optional<DataBaseSE> optimalobj = repo.findById(obj.getId());
		UserSS user = UserSService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !optimalobj.get().getUser().getId().equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}

		DataBaseSE newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		Optional<DataBaseSE> optimalobj = repo.findById(id);
		UserSS user = UserSService.authenticated();
		
		if (user == null || !user.hasRole(Profile.ADMIN) && !optimalobj.get().getUser().getId().equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}
		
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete because there are related orders");
		}
	}

	public Page<DataBaseSE> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserSService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acess denied");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		UserSE userse = userService.find(user.getId());
		return repo.findByUser(userse, pageRequest);
	}
	
	public DataBaseSE fromDTO(DataBaseDTO objDto) {
		return new DataBaseSE(objDto.getId(), objDto.getName(),  null);

	}

	public DataBaseSE fromDTO(DataBaseNewDTO objDto) {
		
		UserSS user = UserSService.authenticated();
		UserSE userse = userService.find(user.getId());
		DataBaseSE database = new DataBaseSE(null, objDto.getName(), userse);
		return database;

	}
	
	private void updateData(DataBaseSE newObj, DataBaseSE obj) {
		newObj.setName(obj.getName());

	}
}
