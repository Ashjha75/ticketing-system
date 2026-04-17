package com.ashish.ticketing.modules.auth.mapper;

import com.ashish.ticketing.modules.auth.dto.response.AuthResponse;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

	private final UserMapper userMapper;

	public AuthMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public AuthResponse toAuthResponse(User user, String accessToken, String refreshToken) {
		return new AuthResponse(accessToken, refreshToken, userMapper.toUserResponse(user));
	}
}
