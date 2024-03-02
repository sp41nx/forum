package telran.java51.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.account.dao.AccountRepository;
import telran.java51.account.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = accountRepository
				.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(username, userAccount.getPassword(), 
				AuthorityUtils
				.createAuthorityList(userAccount
						.getRoles()
						.stream()
						.map(r -> "ROLE_" +  r.name())
						.collect(Collectors.toList())));
	}

}
