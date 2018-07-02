package z.reactivesse.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/*Configuración de la documentación de la API*/
@Configuration
@EnableSwagger2
class Swagger {
    val metaData = ApiInfo("API Docs", "REST API", "1.0", "Terms of service", Contact("Mariano Lopez", "-", ""), "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0")
    val basePackage = "z.reactivesse"
    @Bean
    fun localizacion(): Docket = Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(regex("/stream.*"))
            .build()
            .groupName("0-General")
            .apiInfo(metaData)
}