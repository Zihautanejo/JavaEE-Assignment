import org.springframework.boot.autoconfigure.SpringBootApplication;
import Controller.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ComApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComApplication.class, args);
    }

}
