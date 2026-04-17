package com.ashish.ticketing.modules.auth.service;

import com.ashish.ticketing.common.exception.UnauthorizedException;
import com.ashish.ticketing.modules.auth.dto.request.LoginRequest;
import com.ashish.ticketing.modules.auth.dto.request.RefreshTokenRequest;
import com.ashish.ticketing.modules.auth.dto.request.RegisterRequest;
import com.ashish.ticketing.modules.auth.dto.response.AuthResponse;
import com.ashish.ticketing.modules.auth.dto.response.TokenResponse;
import com.ashish.ticketing.modules.auth.entity.RefreshToken;
import com.ashish.ticketing.modules.auth.mapper.AuthMapper;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

	private final UserService userService;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final AuthMapper authMapper;

	public AuthService(
			UserService userService,
			JwtService jwtService,
			RefreshTokenService refreshTokenService,
			AuthMapper authMapper
	) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.authMapper = authMapper;
	}

	public AuthResponse register(RegisterRequest request) {
		User user = userService.registerUser(request.getEmail(), request.getPassword());
		String accessToken = jwtService.generateAccessToken(user);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
		return authMapper.toAuthResponse(user, accessToken, refreshToken.getToken());
	}

	public AuthResponse login(LoginRequest request) {
		User user = userService.validateUserForLogin(request.getEmail(), request.getPassword());
		String accessToken = jwtService.generateAccessToken(user);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
		return authMapper.toAuthResponse(user, accessToken, refreshToken.getToken());
	}

	@Transactional(readOnly = true)
	public TokenResponse refreshToken(RefreshTokenRequest request) {
		RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
		User user = userService.getActiveUserById(refreshToken.getUserId());
		String accessToken = jwtService.generateAccessToken(user);
		return new TokenResponse(accessToken);
	}

	public void logout(Long userId) {
		refreshTokenService.deleteByUserId(userId);
	}

	public void logoutCurrentUser() {
		User currentUser = userService.getCurrentUser();
		if (currentUser == null || currentUser.getId() == null) {
			throw new UnauthorizedException("No authenticated user found");
		}
		logout(currentUser.getId());
	}
}
