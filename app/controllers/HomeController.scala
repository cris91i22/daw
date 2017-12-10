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
    NoContent
  }

  def blog = Action { implicit request =>
    NoContent
  }

}

case class Bolsones(id: String, name: String, contents: Seq[String], price: BigDecimal, img: String)

case class Fruta(name: String, description: String)