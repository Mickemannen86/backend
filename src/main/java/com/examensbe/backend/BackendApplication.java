package com.examensbe.backend;

import com.examensbe.backend.models.user.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

import static com.examensbe.backend.models.user.Roles.ADMIN;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);

		UserEntity userEntity = new UserEntity();
		userEntity.setRole(ADMIN);

		System.out.println("DEBUGGING " + ADMIN.name());
		System.out.println("DEBUGGING #2 " + userEntity.getAuthorities());
		System.out.println("DEBUGGING #3 " + userEntity.getRole().splitPermissions());

		System.out.println((int) TimeUnit.DAYS.toSeconds(21));

	}

}
