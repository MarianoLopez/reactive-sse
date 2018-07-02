package z.reactivesse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveSseApplication

fun main(args: Array<String>) {
    runApplication<ReactiveSseApplication>(*args)
}
