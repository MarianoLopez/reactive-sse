package z.reactivesse.services

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import z.reactivesse.data.Message

@Service
class MessageService(private val streamService: StreamService) {
    private val messages = mutableListOf<Message>()

    fun subscribe(request: ServerRequest): Mono<ServerResponse> = streamService.subscribe(request)

    fun getMessages(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(
                messages.filter {
                    it.topic==request.pathVariable("topic")
                }.toFlux())
    }
    @Synchronized fun addMessage(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono<Message>()
                .doOnNext {
                    messages.add(it)
                    streamService.insert(it)
                }.flatMap {
                    ServerResponse.ok().body(it.toMono())
                }
    }
}