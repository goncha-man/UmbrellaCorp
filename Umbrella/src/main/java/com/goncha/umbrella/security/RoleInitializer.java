package com.goncha.umbrella.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.goncha.umbrella.entity.Role;
import com.goncha.umbrella.repository.RoleRepository;

@Component
@Order(1)
public class RoleInitializer implements CommandLineRunner {

	private static final List<String> VALID_ROLES = Arrays.asList(
		"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE",
		"ROLE_RECEPTIONIST", "ROLE_PHARMACIST", "ROLE_ANALYST"
	);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) {
		cleanupLegacyRoles();

		createIfNotExists("ROLE_ADMIN",        "Administrador");
		createIfNotExists("ROLE_DOCTOR",       "Doctor");
		createIfNotExists("ROLE_NURSE",        "Enfermero/a");
		createIfNotExists("ROLE_RECEPTIONIST", "Recepcionista");
		createIfNotExists("ROLE_PHARMACIST",   "Farmacéutico");
		createIfNotExists("ROLE_ANALYST",      "Analista");

		System.out.println("[RoleInitializer] Roles ready.");
	}

	private void cleanupLegacyRoles() {
		String inClause = VALID_ROLES.stream()
				.map(n -> "'" + n + "'")
				.collect(Collectors.joining(", "));

		int deleted = jdbcTemplate.update(
			"DELETE FROM role WHERE name NOT IN (" + inClause + ")"
		);
		if (deleted > 0) {
			System.out.println("[RoleInitializer] Removed " + deleted + " legacy role(s).");
		}
	}

	private void createIfNotExists(String name, String description) {
		if (roleRepository.findByName(name).isEmpty()) {
			Role role = new Role();
			role.setName(name);
			role.setDescription(description);
			roleRepository.save(role);
			System.out.println("[RoleInitializer] Created role: " + name);
		}
	}
}
