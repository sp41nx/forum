package telran.java51.account.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AddRoleDto {
	String login;
	Set<String> roles;
}
