package z.reactivesse.services

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import z.reactivesse.models.Message
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@Service
class StreamService {
    private val emitter = PublishSubject.create<Message>()

    fun subscribe(serverSendEventEmitter: SseEmitter,request: HttpServletRequest,topic:String){
        emitter.filter { it.topic==topic }.safeSubscribe(createObserver(sse = serverSendEventEmitter, request = request))
    }

    fun insert(message: Message): Message = message.apply { emitter.onNext(this) }

    private fun createObserver(sse: SseEmitter,request: HttpServletRequest): Observer<Message> {
        return object : Observer<Message> {
            override fun onNext(value: Message) {
                println("${request.remoteAddr}:${request.remotePort} onNext value : $value")
                try{
                    sse.send(SseEmitter.event().apply {
                        id(LocalDateTime.now().toString())
                        data(value, MediaType.APPLICATION_JSON)
                        name("new message")
                    })
                }catch (ise:IllegalStateException){println("error: ${ise.message}")}
            }

            override fun onSubscribe(d: Disposable) {
                println("onSubscribe: ${request.remoteAddr}:${request.remotePort}")
            }

            override fun onError(e: Throwable) {
                println("${request.remoteAddr}:${request.remotePort} onError: ${e.message}")
            }

            override fun onComplete() {
                println("${request.remoteAddr}:${request.remotePort} onComplete")
            }
        }
    }
}