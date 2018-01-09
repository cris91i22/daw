package security

import java.util.Base64

import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

trait BasicAuthAction extends ActionBuilder[Request, AnyContent] with ActionFilter[Request] with PlayBodyParsers {

  val username: String
  val password: String
  val executionContext: ExecutionContext

  private val unauthorized =
    Results.Unauthorized.withHeaders("WWW-Authenticate" -> "Basic realm=Unauthorized")

  def filter[A](request: Request[A]): Future[Option[Result]] = {
    val result = request.headers.get("Authorization") map { authHeader =>
      val (user, pass) = decodeBasicAuth(authHeader)
      if (user == username && pass == password) None else Some(unauthorized)
    } getOrElse Some(unauthorized)

    Future.successful(result)
  }

  private[this] def decodeBasicAuth(authHeader: String): (String, String) = {
    val baStr = authHeader.replaceFirst("Basic ", "")
    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
    val Array(user, password) = new String(decoded).split(":")
    (user, password)
  }

  override def parser: BodyParser[AnyContent] = default

}


class BasicAuthUtil {

  private val authorizationHeaderName = "Authorization"
  private val authorizationRegex = """Basic (.+)""".r
  private val uidApiTokenRegex = """(.+):(.+)""".r

  def extractCredentials(request: Request[_]): Option[Credentials] = {
    request.headers.get(authorizationHeaderName) match {
      case Some(authorizationRegex(base64)) => extractCredentials(base64)
      case _ => None
    }
  }

  private def extractCredentials(base64: String) = {
    val decodedCredentials = Try(new String(Base64.getDecoder.decode(base64)))
    decodedCredentials match {
      case Success(uidApiTokenRegex(uname, apiToken)) =>
        Some(Credentials(uname, apiToken))
      case _ =>
        None
    }
  }
}

case class Credentials(user: String, apiToken: String)