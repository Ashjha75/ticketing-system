package com.ashish.ticketing.modules.user.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.user.dto.request.UpdateUserRequest;
import com.ashish.ticketing.modules.user.dto.response.UserProfileResponse;
import com.ashish.ticketing.modules.user.dto.response.UserResponse;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.enums.UserStatus;
import com.ashish.ticketing.modules.user.mapper.UserMapper;
import com.ashish.ticketing.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "User profile and admin user operations")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@GetMapping("/me")
	@Operation(summary = "Get my profile", description = "Returns the authenticated user's profile")
	public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile() {
		User currentUser = userService.getCurrentUser();
		UserProfileResponse profileResponse = userMapper.toUserProfileResponse(currentUser);
		return ResponseEntity.ok(ApiResponse.success(profileResponse, "User profile fetched successfully"));
	}

	@PutMapping("/me")
	@Operation(summary = "Update my profile", description = "Updates the authenticated user's allowed fields")
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
	@Operation(summary = "Get user by id (admin)", description = "Returns user data by id for admin use")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(ApiResponse.success(userMapper.toUserResponse(user), "User fetched successfully"));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/status")
	@Operation(summary = "Change user status (admin)", description = "Updates a user's status for admin actions")
	public ResponseEntity<ApiResponse<Void>> changeUserStatus(
			@PathVariable Long id,
			@RequestParam UserStatus status
	) {
		userService.changeUserStatus(id, status);
		return ResponseEntity.ok(ApiResponse.success(null, "User status updated successfully"));
	}
}
