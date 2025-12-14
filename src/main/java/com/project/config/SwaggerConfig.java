package com.project.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * 
 * Author: Kody Technolab Ltd. <br/>
 * Date : 22-Sept-2025
 */

@Configuration
public class SwaggerConfig {
	
	@Bean
	 OpenAPI myCustomConfig() {
		return new OpenAPI()
				.info(          //set project info
						new Info()
						.title(" Product-Category  Plateform")     
						.description("By Samradhi pandit")
		         )
				.servers(         //Servers (Environments) define
						Arrays.asList(
						    (new Server().url("http://localhost:8080").description("local")),
						    (new Server().url("http://localhost:8081").description("live"))
					     )
				).addSecurityItem(new SecurityRequirement().addList("bearerAuth")) //Security (JWT Token Authentication)
				.components(new Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
						.in(SecurityScheme.In.HEADER)
						.name("Authoriztion")
				));
	}

}