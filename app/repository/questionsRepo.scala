package repository

import com.google.inject.ImplementedBy
import com.typesafe.config.ConfigFactory
import model._
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID}
import repository.common.BaseRepository
import repository.dao.{QuestionDAO, RespuestaDAO}

import scala.concurrent.Future

@ImplementedBy(classOf[QuestionsRepositoryImpl])
trait QuestionsRepository extends BaseRepository[QuestionDAO, Pregunta] {
  def insert(owner: String, description: String, date: String, respuestas: Seq[Respuesta])(implicit b: BSONDocumentWriter[QuestionDAO]): Future[Pregunta]
}

class QuestionsRepositoryImpl extends QuestionsRepository {
  override val collectionName: String = ConfigFactory.load().getString("db.collections.mensajes")

  override def upConvert(dao: QuestionDAO): Pregunta = {
    def toRespuesta(r: RespuestaDAO): Respuesta = Respuesta(r.description, r.user, r.date)
    Pregunta(dao.id, dao.description, dao.name, dao.date, dao.answers.map(toRespuesta))
  }

  def insert(owner: String, description: String, date: String, respuestas: Seq[Respuesta])(implicit b: BSONDocumentWriter[QuestionDAO]) = {
    def toRespuestaDAO(r: Respuesta): RespuestaDAO = {
      val _id = BSONObjectID.generate
      val id = _id.stringify
      RespuestaDAO(_id, id, r.description, r.user, r.date)
    }

    val _id = BSONObjectID.generate
    val id = _id.stringify
    val dao = QuestionDAO(
      _id,
      id,
      owner,
      description,
      date,
      respuestas.map(toRespuestaDAO)
    )

    this.create(dao)
  }

}
