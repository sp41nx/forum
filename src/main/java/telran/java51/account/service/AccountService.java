package telran.java51.account.service;

import telran.java51.account.dto.AddRoleDto;
import telran.java51.account.dto.UserCreateDto;
import telran.java51.account.dto.UserDto;
import telran.java51.account.dto.UserUpdateDto;

public interface AccountService {
 
	UserDto register(UserCreateDto userCreateDto);
	
	UserDto login();
	
	UserDto delete(String user);
	
	UserDto update(UserUpdateDto userUpdateDto, String login);
	
	AddRoleDto addRole(String user, String role);
	
	AddRoleDto deleteRole(String user, String role);
	
	void changePassword();
	
	UserDto getUser(String user);
	
	
}
