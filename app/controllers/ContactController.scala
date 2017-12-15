package controllers

import javax.inject.Inject

import model.Contact
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.Logger
import repository.ContactRepository

import scala.concurrent.{ExecutionContext, Future}

class ContactController @Inject()(cc: ControllerComponents,
                                  contactRepository: ContactRepository)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {

  private[controllers] val log = Logger

  val contactForm = Form(
    mapping(
      "bolsonId" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "contactNumber" -> nonEmptyText,
      "delivery" -> boolean
    )(Contact.apply)(Contact.unapply)
  )

  def contact = Action.async { implicit request =>
    import repository.handler.BSONDocumentHandlers._

    contactForm.bindFromRequest.fold(
      withErrors => {
        log.error(s"El request fue erroneo $withErrors")
        Future.successful(BadRequest)
      },
      contact => {
        log.info(s"El request fue satisfactorio $contact")
        Ok(views.html.index("Your new application is ready."))
        contactRepository.insert(contact.bolsonId, contact.name, contact.email, contact.contactNumber, contact.delivery)
          .map(_ => Redirect("/")).recover {
          case e: Exception => log.error("Hubo un error en procesar el contacto", e)
            InternalServerError
        }
      }
    )
  }

}