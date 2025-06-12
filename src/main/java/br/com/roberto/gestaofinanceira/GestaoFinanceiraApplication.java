package br.com.roberto.gestaofinanceira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableCaching
public class GestaoFinanceiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoFinanceiraApplication.class, args);
	}

}
