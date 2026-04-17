package com.ashish.ticketing.modules.auth.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.auth.dto.request.LoginRequest;
import com.ashish.ticketing.modules.auth.dto.request.RefreshTokenRequest;
import com.ashish.ticketing.modules.auth.dto.request.RegisterRequest;
import com.ashish.ticketing.modules.auth.dto.response.AuthResponse;
import com.ashish.ticketing.modules.auth.dto.response.TokenResponse;
import com.ashish.ticketing.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication APIs")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	@Operation(summary = "Register user", description = "Creates a new user account and returns access + refresh tokens")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(ApiResponse.success(authService.register(request), "Registration successful"));
	}

	@PostMapping("/login")
	@Operation(summary = "Login user", description = "Validates credentials and returns access + refresh tokens")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(ApiResponse.success(authService.login(request), "Login successful"));
	}

	@PostMapping("/refresh")
	@Operation(summary = "Refresh access token", description = "Generates a new access token using refresh token")
	public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(ApiResponse.success(authService.refreshToken(request), "Token refreshed successfully"));
	}

	@PostMapping("/logout")
	@Operation(summary = "Logout user", description = "Logs out the current user by invalidating refresh token records")
	public ResponseEntity<ApiResponse<Void>> logout() {
		authService.logoutCurrentUser();
		return ResponseEntity.ok(ApiResponse.success(null, "Logout successful"));
	}
}
