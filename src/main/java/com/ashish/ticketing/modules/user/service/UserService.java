package com.ashish.ticketing.modules.user.service;

import com.ashish.ticketing.common.exception.ConflictException;
import com.ashish.ticketing.common.exception.NotFoundException;
import com.ashish.ticketing.common.exception.UnauthorizedException;
import com.ashish.ticketing.common.util.SecurityUtil;
import com.ashish.ticketing.modules.user.dto.request.UpdateUserRequest;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.enums.UserRole;
import com.ashish.ticketing.modules.user.enums.UserStatus;
import com.ashish.ticketing.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(String email, String password) {
		if (userRepository.existsByEmail(email)) {
			throw new ConflictException("Email is already registered");
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(UserRole.USER);
		user.setStatus(UserStatus.ACTIVE);
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Transactional(readOnly = true)
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Transactional(readOnly = true)
	public User getActiveUserById(Long userId) {
		return userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
			.orElseThrow(() -> new UnauthorizedException("User is not active"));
	}

	public User updateUser(Long userId, UpdateUserRequest request) {
		User user = getUserById(userId);

		if (StringUtils.hasText(request.getName())) {
			user.setName(request.getName().trim());
		}

		if (StringUtils.hasText(request.getPassword())) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		return userRepository.save(user);
	}

	public void changeUserStatus(Long userId, UserStatus status) {
		User user = getUserById(userId);
		user.setStatus(status);
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User validateUserForLogin(String email, String rawPassword) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new UnauthorizedException("Invalid email or password");
		}

		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new UnauthorizedException("User account is not active");
		}

		return user;
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		String userId = SecurityUtil.getCurrentUserId();
		if (!StringUtils.hasText(userId)) {
			throw new UnauthorizedException("No authenticated user found");
		}

		try {
			return getUserById(Long.parseLong(userId));
		} catch (NumberFormatException ex) {
			throw new UnauthorizedException("Invalid authenticated user");
		}
	}
}
