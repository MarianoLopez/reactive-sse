package z.reactivesse.services

import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import z.reactivesse.models.Message
import java.time.LocalDateTime


@Service
class StreamService {
    private val emitter =  EmitterProcessor.create<ServerSentEvent<Message>>()


    fun subscribe(topic:String): Flux<ServerSentEvent<Message>> {
        return emitter.filter { it.data()?.topic==topic }.log()
    }

    fun insert(message: Message): Mono<Message> = message.apply { emitter.onNext(ServerSentEvent.builder<Message>().apply {
        data(message)
        id(LocalDateTime.now().toString())
        event("new message")
    }.build())}.toMono()
}