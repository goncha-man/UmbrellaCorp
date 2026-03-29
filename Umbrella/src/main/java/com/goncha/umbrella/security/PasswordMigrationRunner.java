package com.goncha.umbrella.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.goncha.umbrella.entity.User;
import com.goncha.umbrella.repository.UserRepository;

/**
 * Runs once on startup and migrates plain-text passwords to BCrypt.
 * Safe to run multiple times: BCrypt hashes always start with "$2" so
 * already-encoded passwords are skipped automatically.
 */
@Component
public class PasswordMigrationRunner implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		int migrated = 0;
		for (User user : userRepository.findAll()) {
			String pwd = user.getPassword();
			if (pwd != null && !pwd.startsWith("$2")) {
				user.setPassword(passwordEncoder.encode(pwd));
				userRepository.save(user);
				System.out.println("[PasswordMigration] Migrated password for user: " + user.getUsername());
				migrated++;
			}
		}
		if (migrated > 0) {
			System.out.println("[PasswordMigration] Done. " + migrated + " password(s) migrated to BCrypt.");
		} else {
			System.out.println("[PasswordMigration] All passwords are already BCrypt. Nothing to do.");
		}
	}
}
