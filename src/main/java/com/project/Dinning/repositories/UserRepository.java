package com.project.Dinning.repositories;

import java.util.List;

import com.project.Dinning.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByDisplayName(String displayName);
}
