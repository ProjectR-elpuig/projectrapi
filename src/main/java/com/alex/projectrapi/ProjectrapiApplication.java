package com.alex.projectrapi;

import com.alex.projectrapi.model.User;
import com.alex.projectrapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectrapiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ProjectrapiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		userRepo.save(new User("juan", encoder.encode("1234")));
	}
}
