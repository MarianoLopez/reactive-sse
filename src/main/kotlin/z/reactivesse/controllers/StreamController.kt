package z.reactivesse.controllers

import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import z.reactivesse.models.Message
import z.reactivesse.services.StreamService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("stream")
@CrossOrigin(origins = ["*"])
class StreamController(private val streamService: StreamService) {

    @ApiOperation(value = "Subscripción al tópico indicado",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping("/{topic}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE]) fun getStream(@PathVariable topic:String, request: HttpServletRequest): SseEmitter {
        return SseEmitter(Long.MAX_VALUE).apply {
            streamService.subscribe(this,request,topic)
        }
    }

    @ApiOperation(value = "Enviar mensaje",produces = "application/json;charset=UTF-8")
    @PostMapping fun toStream(@RequestBody message:Message): Message = streamService.insert(message)
}