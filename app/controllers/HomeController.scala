package controllers

import javax.inject._

import model._
import play.api.mvc._
import repository.QuestionsRepository

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               questionsRepository: QuestionsRepository)(implicit exec: ExecutionContext)
  extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  def bolsones = Action { implicit request =>
    val bolsones = Seq(Bolsones("0", "Justo para vos (JPV)", Seq("Aji", "Ajo", "Acelga", "Tomates"), 15, "images/caja1.jpg"),
      Bolsones("1", "Familia Tipo (FTP)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo"), 20, "images/caja2.jpg"),
      Bolsones("2", "Familia numerosa (FNM)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo", "Pera", "Wiki", "Manzana"), 25, "images/caja3.jpg"))
    Ok(views.html.bolsones(bolsones))
  }

  def frutas = Action { implicit request =>
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus a urna et nulla dapibus venenatis."
    val frutas = Seq(Fruta("Futa1", text), Fruta("Futa1", text), Fruta("Futa1", text), Fruta("Futa1", text),
      Fruta("Futa1", text), Fruta("Futa1", text), Fruta("Futa1", text), Fruta("Futa1", text))
    Ok(views.html.frutas(frutas))
  }

  def verduras = Action { implicit request =>
    NoContent
  }

  def sucursales = Action { implicit request =>
    val sucur = Seq(Sucursal("Local Madrid", Some("Calle de López de Hoyos, 94, 28002 Madrid"), "917 44 54 00", "9 a 18 hs."),
      Sucursal("Local Valencia", Some("Carrer Catalans, 10, 46001 València, Valencia"), "963 52 54 78", "9 a 18 hs."),
      Sucursal("Casa central", None, "981 14 59 07", "8 a 20 hs."))
    Ok(views.html.sucursales(sucur))
  }

  def faq = Action { implicit request =>
    import repository.handler.BSONDocumentHandlers._
    val preguntas = Await.result(questionsRepository.findAll.map(_.take(3)), 3 seconds)
    Ok(views.html.faq(preguntas))
  }

}

