package hakuna.matata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientTwoApplication.class, args);
    }

    @RestController
    class ServiceInstanceRestController {

        @RequestMapping("/me")
        public String serviceInstancesByApplicationName() {
            return "I am second one";
        }
    }
}
