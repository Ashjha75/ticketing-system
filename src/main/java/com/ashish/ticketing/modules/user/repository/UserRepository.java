package com.ashish.ticketing.modules.user.repository;

import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByIdAndStatus(Long id, UserStatus status);
}
