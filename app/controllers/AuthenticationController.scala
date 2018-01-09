package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class AuthenticationController @Inject()(cc: ControllerComponents)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {

  private[controllers] val log = Logger

  val loginForm = Form(
    tuple(
      "uname" -> text,
      "psw" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (uname, password) => check(uname, password)
    })
  )

  private def check(username: String, password: String) = {
    username == "admin" && password == "1234"
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors.errors.map(_.message))),
      user => Redirect(controllers.routes.HomeController.index.url).withSession("user" -> user._1)
    )
  }

  def logout = Action {
    Redirect(controllers.routes.HomeController.index().url).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }

}
