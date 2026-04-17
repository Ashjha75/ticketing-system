package com.ashish.ticketing.modules.user.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.user.dto.request.UpdateUserRequest;
import com.ashish.ticketing.modules.user.dto.response.UserProfileResponse;
import com.ashish.ticketing.modules.user.dto.response.UserResponse;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.enums.UserStatus;
import com.ashish.ticketing.modules.user.mapper.UserMapper;
import com.ashish.ticketing.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile() {
		User currentUser = userService.getCurrentUser();
		UserProfileResponse profileResponse = userMapper.toUserProfileResponse(currentUser);
		return ResponseEntity.ok(ApiResponse.success(profileResponse, "User profile fetched successfully"));
	}

	@PutMapping("/me")
	public ResponseEntity<ApiResponse<UserProfileResponse>> updateCurrentUserProfile(
			@Valid @RequestBody UpdateUserRequest request
	) {
		User currentUser = userService.getCurrentUser();
		User updatedUser = userService.updateUser(currentUser.getId(), request);
		UserProfileResponse profileResponse = userMapper.toUserProfileResponse(updatedUser);
		return ResponseEntity.ok(ApiResponse.success(profileResponse, "User profile updated successfully"));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(ApiResponse.success(userMapper.toUserResponse(user), "User fetched successfully"));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/status")
	public ResponseEntity<ApiResponse<Void>> changeUserStatus(
			@PathVariable Long id,
			@RequestParam UserStatus status
	) {
		userService.changeUserStatus(id, status);
		return ResponseEntity.ok(ApiResponse.success(null, "User status updated successfully"));
	}
}
