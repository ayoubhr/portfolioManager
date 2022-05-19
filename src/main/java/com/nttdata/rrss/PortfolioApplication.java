package com.nttdata.rrss;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nttdata.rrss.Entity.Role;
import com.nttdata.rrss.Services.RoleService;

@SpringBootApplication
@ComponentScan
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT","DELETE");
            }
        };
    }
	// Script that adds roles to the roles table in database. Reads the roles array and checks if table in db is up to date with it.
	@Bean
	CommandLineRunner run(RoleService roleService) {
		return args -> {
			String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
			List<Role> allRolesInDb = roleService.findRoles();
			
			if(allRolesInDb.size()>0) {
				for (int i = 0; i < roles.length; i++) {
					if(roles.length > allRolesInDb.size() || roles.length == allRolesInDb.size()) {
						if(roleService.findRoleByName(roles[i]) == null) {
							roleService.saveRole(new Role(roles[i]));
						}
					} else {
						roleService.deleteRoleById(allRolesInDb.get(roles.length).getId());
						break;
					}
				}
			} else if(allRolesInDb.size()==0) {
				for(int j = 0; j<roles.length; j++) {
					roleService.saveRole(new Role(roles[j]));
				}
			}
		};
	}
	
	
}
