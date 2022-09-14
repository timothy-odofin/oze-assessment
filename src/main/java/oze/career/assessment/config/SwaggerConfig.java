package oze.career.assessment.config;


/**
 *
 * @author JIDEX
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("oze.career.assessment.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Hospital Service application that supports the migration of\n" +
                        " Staff and other medical record").description("")
                .termsOfServiceUrl("https://oze-medical.org")
                .contact(new Contact("Oze Med", "https://oze-medical.org", "admin@oze-medical.org"))
                .license("Open Source").licenseUrl("https://oze-medical.org").version("1.0.0").build();
    }

}
