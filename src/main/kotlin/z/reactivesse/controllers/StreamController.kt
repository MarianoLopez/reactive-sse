package z.reactivesse.controllers

import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import z.reactivesse.models.Message
import z.reactivesse.services.StreamService

@RestController
@RequestMapping("stream")
@CrossOrigin(origins = ["*"])
class StreamController(private val streamService: StreamService) {


    @GetMapping("/{topic}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE]) fun getStream(@PathVariable topic:String): Flux<ServerSentEvent<Message>> {
        return streamService.subscribe(topic)
    }

    @PostMapping fun toStream(@RequestBody message:Message): Mono<Message> = streamService.insert(message)
}