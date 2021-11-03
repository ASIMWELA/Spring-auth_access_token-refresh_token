package com.jwt.refresh.token;

import com.jwt.refresh.token.role.ERole;
import com.jwt.refresh.token.role.Role;
import com.jwt.refresh.token.role.RoleRepository;
import com.jwt.refresh.token.user.User;
import com.jwt.refresh.token.user.UserRepository;
import com.jwt.refresh.token.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class AuthenticationJwtApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationJwtApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserService userService, RoleRepository roleRepository){
		return args -> {
					Role roleAdmin = new Role();
					roleAdmin.setRoleName(ERole.ROLE_ADMIN);
					Role roleUser = new Role();
					roleUser.setRoleName(ERole.ROLE_USER);
					Role roleSuperAdmin = new Role();
					roleSuperAdmin.setRoleName(ERole.ROLE_SUPER_ADMIN);

					roleRepository.saveAll(
							Stream.of(roleAdmin, roleSuperAdmin, roleUser).collect(Collectors.toList())
					);

					User user = new User( "Augustine", "Simwela", "asimwela", "12345", true);
					user.setRoles(Stream.of(roleSuperAdmin, roleUser).collect(Collectors.toList()));
					userService.saveUser(user);

					User user2 = new User("Victoria","Simwela", "vsimwela","12345", true);
					user2.setRoles(Collections.singletonList(roleUser));
			        userService.saveUser(user2);

					User user3 = new User("Nelson","Simwela","nsimwela","12345", true);
					user3.setRoles(Collections.singletonList(roleAdmin));
					userService.saveUser(user3);

					User user4 = new User("Salome","Simwela","ssimwela","12345", true);
					user4.setRoles(Stream.of(roleAdmin, roleUser).collect(Collectors.toList()));
					userService.saveUser(user4);
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}
}
