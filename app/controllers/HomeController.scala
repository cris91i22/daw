package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  val bolsonesk = Seq(Bolsones("0", "Justo para vos (JPV)", Seq("Aji", "Ajo", "Acelga", "Tomates"), 15, "caja1"),
    Bolsones("1", "Familia Tipo (FTP)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo"), 20, "caja2"),
    Bolsones("2", "Familia numerosa (FNM)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo", "Pera", "Wiki", "Manzana"), 25, "caja3"))


  def bolsones = Action { implicit request =>
    val bolsones = Seq(Bolsones("0", "Justo para vos (JPV)", Seq("Aji", "Ajo", "Acelga", "Tomates"), 15, "images/caja1.jpg"),
      Bolsones("1", "Familia Tipo (FTP)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo"), 20, "images/caja2.jpg"),
      Bolsones("2", "Familia numerosa (FNM)", Seq("Aji", "Ajo", "Acelga", "Tomates", "Calabaza", "Repollo", "Pera", "Wiki", "Manzana"), 25, "images/caja3.jpg"))
    Ok(views.html.bolsones(bolsones))
  }

  def frutas = Action { implicit request =>
    Ok(views.html.bolsones(bolsonesk))
  }

  def verduras = Action { implicit request =>
    Ok(views.html.bolsones(bolsonesk))
  }

  def sucursales = Action { implicit request =>
    Ok(views.html.bolsones(bolsonesk))
  }

  def blog = Action { implicit request =>
    Ok(views.html.bolsones(bolsonesk))
  }

}

case class Bolsones(id: String, name: String, contents: Seq[String], price: BigDecimal, img: String)