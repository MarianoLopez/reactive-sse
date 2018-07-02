package z.reactivesse.services

import org.springframework.stereotype.Service
import org.springframework.http.codec.ServerSentEvent
import reactor.core.publisher.EmitterProcessor
import z.reactivesse.models.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.LocalDateTime


@Service
class StreamService {
    private val emitter = EmitterProcessor.create<ServerSentEvent<Message>>()
    //TODO volver a SseEmitter

    fun getInfiniteMessages(): Flux<ServerSentEvent<Message>> = emitter.log().doOnSubscribe { println("sub: ${it.toMono()}") }.doOnError { println("error: ${it.message}") }.doOnTerminate { println("terminate") }.doOnCancel { println("cancel") }.doOnComplete { println("complete") }

    fun insert(message: Message): Mono<Message> {
        emitter.onNext(ServerSentEvent.builder<Message>()
                .id(LocalDateTime.now().toString())
                .event("new message")
                .data(message)
                .build())
        return message.toMono()
    }
}