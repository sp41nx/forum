package telran.java51.security;

import java.util.function.Supplier;

import javax.management.relation.Role;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;

import lombok.RequiredArgsConstructor;
import telran.java51.constants.Roles;
import telran.java51.post.dao.PostRepository;

@Configuration
@RequiredArgsConstructor
public class AuthorizationConfiguration {
	
	
	final CustomWebSecurity customWebSecurity;
	
	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.httpBasic(Customizer.withDefaults());
//		http.authorizeHttpRequests(authorize -> authorize
//				.anyRequest().permitAll()
//				);
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/account/register", "forum/posts/**")
					.permitAll()
				.requestMatchers("/account/user/{login}/role/{role}")
					.hasRole(Roles.ADMINISTRATOR.name())
				.requestMatchers(HttpMethod.PUT, "/account/user/{login}")
					.access(new WebExpressionAuthorizationManager("#login == authentication.name"))
				.requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
					.access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))
				.requestMatchers(HttpMethod.POST, "/forum/post/{author}")
					.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
				.requestMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}")
					.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
				.requestMatchers(HttpMethod.PUT, "/forum/post/{id}")
				    .access((authentication, context) -> 
				    		new AuthorizationDecision(
				    				customWebSecurity.checkPostAuthor(
				    						context.getVariables().get("id"),
				    						authentication.get().getName())))
				.requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")    
					.access(new AuthorizationManager<RequestAuthorizationContext>() {		
						
						@Override
						public AuthorizationDecision check(Supplier<Authentication> authentication,
								RequestAuthorizationContext context) {
							
							return new AuthorizationDecision(customWebSecurity.checkPostAuthor(
		    						context.getVariables().get("id"),
		    						authentication.get().getName()) || context.getRequest().isUserInRole("MODERATOR"));
						}
						
				})
					.anyRequest()
					.authenticated()
				);
		return http.build();
		
	}

}
