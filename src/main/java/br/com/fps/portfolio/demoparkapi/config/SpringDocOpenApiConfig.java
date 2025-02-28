package br.com.fps.portfolio.demoparkapi.config;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

  @Bean
  public OpenAPI openAPI(){
    return new OpenAPI()
        .info(new Info()
            .title("REST API - Spring Park")
            .description("API para gestão de estacionamento de veículos")
            .version("1.0")
            .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
        );

  }


}
