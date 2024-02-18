package telran.java51.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserCreateDto {
	
	String login;
	String password;
	String firstName;
	String lastName;
	
}
