package com.ashish.ticketing.modules.auth.service;

import com.ashish.ticketing.common.exception.UnauthorizedException;
import com.ashish.ticketing.modules.auth.entity.RefreshToken;
import com.ashish.ticketing.modules.auth.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final long refreshTokenExpirationMs;

	public RefreshTokenService(
			RefreshTokenRepository refreshTokenRepository,
			@Value("${auth.refresh-token-expiration:604800000}") long refreshTokenExpirationMs
	) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.refreshTokenExpirationMs = refreshTokenExpirationMs;
	}

	public RefreshToken createRefreshToken(Long userId) {
		refreshTokenRepository.deleteByUserId(userId);

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUserId(userId);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
		return refreshTokenRepository.save(refreshToken);
	}

	@Transactional(readOnly = true)
	public RefreshToken validateRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
			.orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			throw new UnauthorizedException("Refresh token has expired");
		}

		return refreshToken;
	}

	public void deleteByUserId(Long userId) {
		refreshTokenRepository.deleteByUserId(userId);
	}
}
