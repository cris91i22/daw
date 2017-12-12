package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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
    val respuestas = Seq(Respuesta("La verdad que la calidad del servicio es genial, hasta el dia de hoy no tuve problemas" +
      "en contactar y en la mercaderia que me trajeron", "Juan Monaco", "15/01/2015"),
      Respuesta("Hasta ahora cumplen con todo lo informado", "Ricardo Morales", "02/04/2016"))
    val preguntas = Seq(Pregunta(1, "Como es la calidad del servicio?", "Mi huerta organica", "01/01/2015", respuestas))
    Ok(views.html.faq(preguntas))
  }

}

case class Bolsones(id: String, name: String, contents: Seq[String], price: BigDecimal, img: String)

case class Fruta(name: String, description: String)

case class Sucursal(name: String, location: Option[String], tel: String, attendanceTime: String)

case class Pregunta(id: Int, description: String, owner: String, date: String, answers: Seq[Respuesta])

case class Respuesta(description: String, owner: String, date: String)