package com.goncha.umbrella.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.goncha.umbrella.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
