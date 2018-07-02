package z.reactivesse.controllers

import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.http.server.reactive.ServerHttpRequest

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import z.reactivesse.models.Message
import z.reactivesse.services.StreamService

@RestController
@RequestMapping("stream")
@CrossOrigin(origins = ["*"])
class Main(private val streamService: StreamService) {

    @GetMapping fun getStream(request: ServerHttpRequest): Flux<ServerSentEvent<Message>>{
        println("ip: ${request.remoteAddress}")
        return streamService.getInfiniteMessages().doOnCancel { println("canceled") }
    }

    @PostMapping fun toStream(@RequestBody message:Message): Mono<Message> = streamService.insert(message)
}