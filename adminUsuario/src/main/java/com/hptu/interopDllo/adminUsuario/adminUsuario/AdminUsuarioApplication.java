package com.hptu.interopDllo.adminUsuario.adminUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AdminUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminUsuarioApplication.class, args);
	}

}
