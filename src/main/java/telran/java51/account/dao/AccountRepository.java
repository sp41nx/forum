package telran.java51.account.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.account.model.UserAccount;

public interface AccountRepository extends CrudRepository<UserAccount, String>{
	

}
