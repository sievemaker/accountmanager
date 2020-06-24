package io.github.lrzeszotarski.accountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AccountmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountmanagerApplication.class, args);
    }

}
