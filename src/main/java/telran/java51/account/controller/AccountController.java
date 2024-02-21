package telran.java51.account.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.account.dto.AddRoleDto;
import telran.java51.account.dto.UserCreateDto;
import telran.java51.account.dto.UserDto;
import telran.java51.account.dto.UserUpdateDto;
import telran.java51.account.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	
	final AccountService accountService;
	
	@PostMapping("/register")
	public UserDto register(@RequestBody UserCreateDto userCreateDto) {
		return accountService.register(userCreateDto); 
	}
	
//	@PostMapping("/login")
//	public UserDto login(@RequestHeader("Authorization") String token) {
//		token = token.split(" ")[1];
//		String credentials = new String(Base64.getDecoder().decode(token));
//		return accountService.getUser(credentials.split(":")[0]);
//	}
	
	@PostMapping("/login")
	public UserDto login(Principal principal) {
		return accountService.getUser(principal.getName());
	}
	
	@DeleteMapping("/user/{user}")
	public UserDto deleteUser(@PathVariable String user) {
		return accountService.delete(user);
		
	}
	
	@PutMapping("/user/{user}")
	public UserDto updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable String user) {
		return accountService.update(userUpdateDto, user);
	}
	
	@PutMapping("/user/{user}/role/{role}")
	public AddRoleDto AddRole(@PathVariable String user, @PathVariable String role) {
		return accountService.addRole(user, role);
	}
	
	@DeleteMapping("/user/{user}/role/{role}")
	public AddRoleDto deleteRole(@PathVariable String user, @PathVariable String role) {
		return accountService.deleteRole(user, role);
	}
	
	@PutMapping("/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(Principal principal, @RequestHeader("X-Password") String newPassword) {
		accountService.changePassword(principal.getName(), newPassword);
	}
	
	@GetMapping("/user/{user}")
	public UserDto getUser(@PathVariable String user) {
		return accountService.getUser(user);
	}
	
	
}
