package com.project.Dinning.repositories;

import com.project.Dinning.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByDisplayNameIgnoreCase(String displayName);
}
