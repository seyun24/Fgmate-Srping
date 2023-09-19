package com.example.hjs.src.dao.user;

import com.example.hjs.src.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByInfoId(Long id);
    User findByInfoId(Long id);
}
