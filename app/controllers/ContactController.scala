package controllers

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.Logger

class ContactController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private[controllers] val log = Logger

  val contactForm = Form(
    mapping(
      "bolsonId" -> number,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "contactNumber" -> nonEmptyText,
      "delivery" -> boolean
    )(Contact.apply)(Contact.unapply)
  )

  def contact = Action { implicit request =>
    contactForm.bindFromRequest.fold(
      withErrors => {
        log.error(s"El request fue erroneo $withErrors")
        Redirect("/")
      },
      contact => {
        log.info(s"El request fue satisfactorio $contact")
        Ok(views.html.index("Your new application is ready."))
      }
    )
  }

}


case class Contact(bolsonId: Int, name: String, email: String, contactNumber: String, delivery: Boolean)