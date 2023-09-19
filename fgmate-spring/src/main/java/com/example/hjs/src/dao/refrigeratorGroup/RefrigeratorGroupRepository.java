package com.example.hjs.src.dao.refrigeratorGroup;

import com.example.hjs.src.domain.refrigeratorGroup.RefrigeratorGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefrigeratorGroupRepository extends JpaRepository<RefrigeratorGroup,Long> {
    boolean existsByRefrigeratorIdAndUserId(Long id, Long userId);
    Optional<RefrigeratorGroup> findByRefrigeratorIdAndUserId(Long id, Long userId);
}
