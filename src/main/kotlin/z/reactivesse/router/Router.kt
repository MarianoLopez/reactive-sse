package z.reactivesse.router


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import z.reactivesse.services.StreamService


@Configuration
class Router {

    @Bean fun streamRouter(streamService: StreamService) = router {
        "/stream".nest {
            GET("/{topic}", streamService::subscribe)
            POST("/", streamService::insert)
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