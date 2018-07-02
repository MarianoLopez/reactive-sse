package z.reactivesse.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller("/")
class HtmlController {
    @GetMapping
    fun index(model: Model, @RequestParam topic:String?) = "index".also { model.addAttribute("topic",topic?:"all") }
}