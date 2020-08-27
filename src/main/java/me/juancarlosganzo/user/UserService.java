package me.juancarlosganzo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.juancarlosganzo.entity.UserEntity;
import me.juancarlosganzo.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserEntity> GetUserList() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity GetUserByEmail(String email) throws UserException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UserException("No se encontro el correo");
		}

		return userEntity;
	}

	@Override
	public UserEntity GetUserById(int id) throws UserException {

		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new UserException("No se encontro el id"));
		return userEntity;
	}

	@Override
	public int Cretate(UserEntity userEntity) throws UserException {

		UserEntity userCorreoEntity = userRepository.findByEmail(userEntity.getEmail());
		if (userCorreoEntity != null) {
			throw new UserException("El correo ya existe en la base de datos.");
		}
		userRepository.save(userEntity);
		return userEntity.getIdUser();
	}

	@Override
	public int Edit(UserEntity userEntity) throws UserException {

		UserEntity userCorreoEntity = userRepository.findByEmail(userEntity.getEmail());
		if (userCorreoEntity != null) {
			if (userCorreoEntity.getIdUser() != userEntity.getIdUser()) {
				throw new UserException("El correo ya existe en la base de datos.");
			}
		}
		UserEntity user = GetUserById(userEntity.getIdUser());
		user.setEmail(userEntity.getEmail());
		user.setName(userEntity.getName());
		user.setPhone(userEntity.getPhone());
		user.setUsername(userEntity.getUsername());
		userRepository.save(user);
		return user.getIdUser();
	}

}
