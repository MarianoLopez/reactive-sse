package z.reactivesse.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/")
class HtmlController {
    @GetMapping fun index(model: Model, @RequestParam topic:String?) = "index".also { model.addAttribute("topic",topic?:"all") }
    @GetMapping("swagger") fun swaggerRedirect() = RedirectView("swagger-ui.html")
}