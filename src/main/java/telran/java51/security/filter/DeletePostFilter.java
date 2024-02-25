package telran.java51.security.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java51.account.dao.AccountRepository;
import telran.java51.account.exceptions.UserNotFoundException;
import telran.java51.account.model.User;
import telran.java51.en.Roles;
import telran.java51.post.dao.PostRepository;
import telran.java51.post.dto.exceptions.PostNotFoundException;
import telran.java51.post.model.Post;

@Component
@RequiredArgsConstructor
@Order(15)
public class DeletePostFilter implements Filter {
	
	final PostRepository postRepository;
	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
        if(checkEndPoint(request.getMethod(), request.getServletPath())) {
        	String[] pathParts = request.getServletPath().split("/");
			String postId = pathParts[pathParts.length - 1];
        	Post post ;
        	User user = accountRepository.findById(request.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
			try {
				post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
			} catch (PostNotFoundException e) {
				response.sendError(404, "Post not found");
				return;
			}
        	if(!(post.getAuthor().equalsIgnoreCase(request.getUserPrincipal().getName()) || user.getRoles().contains(Roles.MODERATOR))) {
        		response.sendError(403, "Permission denied");
        		return;
        	}
        }		

		chain.doFilter(request, response);
		
	}
	
	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+");
	}

}
