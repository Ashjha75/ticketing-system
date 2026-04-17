package com.ashish.ticketing.modules.user.mapper;

import com.ashish.ticketing.modules.user.dto.response.UserProfileResponse;
import com.ashish.ticketing.modules.user.dto.response.UserResponse;
import com.ashish.ticketing.modules.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserResponse toUserResponse(User user) {
		if (user == null) {
			return null;
		}

		return new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getRole() == null ? null : user.getRole().name(),
			user.getStatus() == null ? null : user.getStatus().name()
		);
	}

	public UserProfileResponse toUserProfileResponse(User user) {
		if (user == null) {
			return null;
		}

		return new UserProfileResponse(
			user.getId(),
			user.getEmail(),
			user.getRole() == null ? null : user.getRole().name(),
			user.getStatus() == null ? null : user.getStatus().name(),
			user.getCreatedAt()
		);
	}
}
