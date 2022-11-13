package com.zk.uc.repository;

import com.zk.uc.root.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")

public interface UserRepository extends JpaRepository<User, Long> {
}
