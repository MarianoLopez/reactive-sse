package z.reactivesse.controllers

import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import z.reactivesse.models.Message
import z.reactivesse.services.StreamService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("stream")
@CrossOrigin(origins = ["*"])
class StreamController(private val streamService: StreamService) {

    @GetMapping("/{topic}") fun getStream(@PathVariable topic:String, request: HttpServletRequest): SseEmitter {
        return SseEmitter(Long.MAX_VALUE).apply {
            streamService.subscribe(this,request,topic)
        }
    }

    @PostMapping fun toStream(@RequestBody message:Message): Message = streamService.insert(message)
}