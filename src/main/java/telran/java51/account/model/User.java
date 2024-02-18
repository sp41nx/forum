package telran.java51.account.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "login")
@Document(collection = "account")
public class User {
	
	@Getter
	@Id
	String login;
	@Getter
	@Setter
	String firstName;
	@Getter
	@Setter
	String lastName;
	@Setter
	String password;
	@Getter
	Set<String> roles;
	
	
	public User() {
		this.roles = new HashSet<String>();
		roles.add("USER");
	}
	
	public User(String login, String password) {
		this();
		this.login = login;
		this.password = password;
	}
	
	public User(String login, String password, String firstName, String lastName) {
		this(login, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = new HashSet<String>();
	}

	
	public boolean addRole(String role) {
		return roles.add(role);
	}
	
	public boolean deleteRole(String role) {
		return roles.remove(role);
	}
	
}
