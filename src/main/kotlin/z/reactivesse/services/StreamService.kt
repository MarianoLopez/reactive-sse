package z.reactivesse.services

import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import z.reactivesse.data.Message
import java.time.LocalDateTime


@Service
class StreamService {
    private val emitter =  EmitterProcessor.create<ServerSentEvent<Message>>()


    fun subscribe(request: ServerRequest): Mono<ServerResponse> {
        val topic = request.pathVariable("topic")
        return ok().body(emitter.filter { it.data()?.topic==topic }.log())
    }

    fun insert(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono<Message>()
                .doOnNext {
                    emitter.onNext(ServerSentEvent.builder<Message>().apply {
                        data(it)
                        id(LocalDateTime.now().toString())
                        event("new message")
                    }.build())
                }.flatMap { ok().body(it.toMono()) }
    }
}