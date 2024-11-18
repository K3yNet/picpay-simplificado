package com.pagamentos.picpay_simplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PicPaySimplificadoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PicPaySimplificadoApplication.class, args);
	}

}