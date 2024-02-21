package telran.java51.account.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.account.dao.AccountRepository;
import telran.java51.account.dto.AddRoleDto;
import telran.java51.account.dto.UserCreateDto;
import telran.java51.account.dto.UserDto;
import telran.java51.account.dto.UserUpdateDto;
import telran.java51.account.exceptions.UserNotFoundException;
import telran.java51.account.model.User;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	final AccountRepository accountRepository;
	final ModelMapper modelMapper;

	@Override
	public UserDto register(UserCreateDto userCreateDto) {
		User user = modelMapper.map(userCreateDto, User.class); 
		String password = BCrypt.hashpw(userCreateDto.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		user = accountRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto login(String login) {
		User user = accountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto delete(String login) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		accountRepository.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto update(UserUpdateDto userUpdateDto, String login) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.setFirstName(userUpdateDto.getFirstName());
		user.setLastName(userUpdateDto.getLastName());
		accountRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public AddRoleDto addRole(String login, String role) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.addRole(role);
		accountRepository.save(user);
		return modelMapper.map(user, AddRoleDto.class);
	}

	@Override
	public AddRoleDto deleteRole(String login, String role) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.deleteRole(role);
		accountRepository.save(user);
		return modelMapper.map(user, AddRoleDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.setPassword(newPassword);
		accountRepository.save(user);
	}

	@Override
	public UserDto getUser(String login) {
		User user = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserDto.class);
	}

}
