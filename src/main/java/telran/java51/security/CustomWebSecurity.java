package telran.java51.security;


import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.post.dao.PostRepository;
import telran.java51.post.model.Post;

@Service("customWebSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity implements AuthenticationManager{
	
	final PostRepository postRepository;
	
	public boolean checkPostAuthor(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		return post != null && userName.equals(post.getAuthor());
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
