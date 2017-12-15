package controllers

import javax.inject._

import model._
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json._
import play.api.mvc._
import repository.{QuestionsRepository, VegetablesRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AsyncController @Inject()(cc: ControllerComponents,
                                vegetablesRepository: VegetablesRepository,
                                questionsRepository: QuestionsRepository)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {


  def vegetablesBulk() = Action.async { implicit request =>
    import VegetableController._
    import repository.handler.BSONDocumentHandlers._
    vegetablesRepository.insert("COCO", "BONGO", 10.0).map(k => Ok(Json.toJson(k)))
  }

  def preguntasBulk() = Action.async { implicit request =>
    import repository.handler.BSONDocumentHandlers._
    import controllers.Preguntas._
    import MockData._
    Future.sequence(preguntas.map { p =>
      (questionsRepository.insert _).tupled(p)
    }).map(n => Ok(Json.toJson(n)))
  }

  def moreQuestions() = Action.async { implicit request =>
    import repository.handler.BSONDocumentHandlers._
    import controllers.Preguntas._
    questionsRepository.findAll.map(_.drop(3)).map(p => Ok(Json.toJson(p)))
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

object Preguntas {
  implicit val respuestaContract: Format[Respuesta] = Json.format[Respuesta]

  implicit val preguntaContract: Format[Pregunta] = (
    (JsPath \ "id").format[String] and
      (JsPath \ "description").format[String] and
      (JsPath \ "owner").format[String] and
      (JsPath \ "date").format[String] and
      (JsPath \ "answers").format[Seq[Respuesta]]
    )(Pregunta.apply, unlift(Pregunta.unapply))
}


object MockData {


  private val respuestas = Seq(Respuesta("La verdad que la calidad del servicio es genial, hasta el dia de hoy no tuve problemas" +
    "en contactar y en la mercaderia que me trajeron", "Juan Monaco", "15/01/2015"),
    Respuesta("Hasta ahora cumplen con todo lo informado", "Ricardo Morales", "02/04/2016"))


  private val pregunta1 = ("Mi huerta organica", "Como es la calidad del servicio?", "01/01/2015", respuestas)
  private val pregunta2 = ("Mi huerta organica", "Los productos son variados?", "01/05/2015", respuestas)
  private val pregunta3 = ("Mi huerta organica", "El delivery cumple con los plazos?", "01/08/2015", respuestas)
  private val pregunta4 = ("Mi huerta organica", "La atencion en las sedes es rapida?", "13/03/2016", respuestas)
  private val pregunta5 = ("Mi huerta organica", "Que es lo mas destacable?", "16/09/2016", respuestas)
  private val pregunta6 = ("Mi huerta organica", "Hay otras sedes?", "20/01/2017", respuestas)
  private val pregunta7 = ("Mi huerta organica", "Tienen pensado ampliar su comercio?", "09/08/2017", respuestas)

  val preguntas = Seq(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7)

}