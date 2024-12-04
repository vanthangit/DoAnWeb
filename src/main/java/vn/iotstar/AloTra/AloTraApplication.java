package vn.iotstar.AloTra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AloTraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AloTraApplication.class, args);
	}

}
