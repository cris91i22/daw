package controllers

import javax.inject._

import model.Vegetable
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json._
import play.api.mvc._
import repository.VegetablesRepository

import scala.concurrent.ExecutionContext

@Singleton
class AsyncController @Inject()(cc: ControllerComponents,
                                vegetablesRepository: VegetablesRepository)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {


  def vegetablesBulk() = Action.async { implicit request =>
    import VegetableController._
    import repository.handler.BSONDocumentHandlers._
    vegetablesRepository.insert("COCO", "BONGO", 10.0).map(k => Ok(Json.toJson(k)))
  }

}

object VegetableController {
  implicit val vegetableContract: Format[Vegetable] = (
    (JsPath \ "id").format[String] and
      (JsPath \ "name").format[String] and
      (JsPath \ "description").format[String] and
      (JsPath \ "price").format[Double]
  )(Vegetable.apply, unlift(Vegetable.unapply))
}
