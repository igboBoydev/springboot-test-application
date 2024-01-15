package com.TestApplication.Repository;

import com.TestApplication.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET enabled = true WHERE email = :email", nativeQuery = true)
    int enableUser(String email);
}
