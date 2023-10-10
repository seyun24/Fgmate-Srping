package org.siksnaghae.fgmate.api.group.repository;

import org.siksnaghae.fgmate.api.group.model.RefrigeratorGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefrigeratorGroupRepository extends JpaRepository<RefrigeratorGroup,Long> {
    boolean existsByRefrigeratorIdAndUserId(Long id, Long userId);
    Optional<RefrigeratorGroup> findByRefrigeratorIdAndUserId(Long id, Long userId);
}
