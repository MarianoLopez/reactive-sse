package z.reactivesse.configuration

import io.github.cdimascio.swagger.Validate

object Swagger {
    val validate = Validate.configure("static/api.json")
}