package telran.java51.account.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import telran.java51.constants.Roles;

@EqualsAndHashCode(of = "login")
@Document(collection = "account")
public class UserAccount {
	
	@Getter
	@Id
	String login;
	@Getter
	@Setter
	String firstName;
	@Getter
	@Setter
	String lastName;
	@Getter
	@Setter
	String password;
	@Getter
	Set<Roles> roles;
	
	
	public UserAccount() {
		this.roles = new HashSet<Roles>();
		roles.add(Roles.USER);
	}
	
	public UserAccount(String login, String password) {
		this();
		this.login = login;
		this.password = password;
	}
	
	public UserAccount(String login, String password, String firstName, String lastName) {
		this(login, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = new HashSet<Roles>();
	}

	
	public boolean addRole(String role) {
		return roles.add(Roles.valueOf(role.toUpperCase()));
	}
	
	public boolean deleteRole(String role) {
		return roles.remove(Roles.valueOf(role.toUpperCase()));
	}
	
}
