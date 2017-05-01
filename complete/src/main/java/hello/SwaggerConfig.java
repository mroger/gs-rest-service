package hello;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {          
	
    @Bean
    public Docket greetingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("hello"))
          .paths(PathSelectors.regex("/api/greeting.*"))
          .build()
          .useDefaultResponseMessages(false)
          .globalResponseMessage(RequestMethod.GET,
        		  newArrayList(new ResponseMessageBuilder()
                      .code(500)
                      .message("Internal Server Error")
                      .responseModel(new ModelRef("Error"))
                      .build()))
          .globalOperationParameters(
                  newArrayList(new ParameterBuilder()
                      .name("greet-subject")
                      .defaultValue("subject-name")
                      .description("Subject authorized to access this resource")
                      .modelRef(new ModelRef("string"))
                      .parameterType("header")
                      .required(true)
                      .build()))
          .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "Greetings API",
          "This API is used as an example of how to implement a Swagger 2 documentation.",
          "1.0",
          "API TOS",
          contact(),
          "License of API",
          "license.html");
        return apiInfo;
    }

	private Contact contact() {
		return new Contact("Roger", "https://mr0ger.wordpress.com/", "roger@domain.com");
	}
}