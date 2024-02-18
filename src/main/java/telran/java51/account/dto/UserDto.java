package telran.java51.account.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
public class UserDto {
	
	String login;
	String firstName;
	String lastName;
	Set<String> roles;
	
	
}
