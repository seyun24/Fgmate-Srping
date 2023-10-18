package org.siksnaghae.fgmate.api.user.repository;

import org.siksnaghae.fgmate.api.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByInfoId(String id);
    User findByInfoId(String id);

    List<User> findByNameStartingWith(String name);
}
