package com.ashish.ticketing.modules.auth.service;

import com.ashish.ticketing.config.JwtTokenProvider;
import com.ashish.ticketing.modules.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String generateAccessToken(User user) {
		String role = user.getRole() == null ? null : user.getRole().name();
		return jwtTokenProvider.generateToken(String.valueOf(user.getId()), role);
	}

	public boolean validateToken(String token) {
		return jwtTokenProvider.validateToken(token);
	}

	public Long extractUserId(String token) {
		return Long.parseLong(jwtTokenProvider.getUserId(token));
	}
}
