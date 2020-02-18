package hakuna.matata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientOneApplication.class, args);
    }

    @RestController
    class ServiceInstanceRestController {

        @RequestMapping("/me")
        public String serviceInstancesByApplicationName() {
            return "I am first one";
        }
    }
}
