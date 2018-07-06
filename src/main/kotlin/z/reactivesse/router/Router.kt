package z.reactivesse.router


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import z.reactivesse.services.MessageService


@Configuration
class Router {

    @Bean fun messageRouter(messageService: MessageService) = router {
        "/message".nest {
            GET("/stream/{topic}", messageService::subscribe)
            GET("/{topic}", messageService::getMessages)
            POST("/", messageService::addMessage)
        }
    }

    @Bean fun htmlRouter() = router {
        accept(MediaType.TEXT_HTML).nest{
            GET("/"){ ok().render("ServerSendEventsJSExample",it.queryParams()) }
            GET("/swagger"){ok().render("swagger/swagger")}
        }
        resources("/**", ClassPathResource("static/"))
    }
}