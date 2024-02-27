package telran.java51.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java51.account.dao.AccountRepository;
import telran.java51.account.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(40)
public class UpdateByOwnerFilter implements Filter {
	
	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if(checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();
			String[] pathParts = request.getServletPath().split("/");
			String login = pathParts[pathParts.length - 1];
			if(!principal.getName().equalsIgnoreCase(login)) {
				response.sendError(403, "Permission denied");
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.PUT.matches(method) && path.matches("/account/user/\\w+");
	}
	
}
