package repository.handler

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID, DefaultBSONHandlers, Macros}
import repository.dao.{ContactDAO, QuestionDAO, RespuestaDAO, VegetableDAO}

object BSONDocumentHandlers extends DefaultBSONHandlers {

  implicit val vegetableHandler = Macros.handler[VegetableDAO]
  implicit val contactHandler = Macros.handler[ContactDAO]
  implicit val respuestaHandler = Macros.handler[RespuestaDAO]

  implicit object QuestionWriter extends BSONDocumentWriter[QuestionDAO] {
    def write(q: QuestionDAO): BSONDocument = BSONDocument(
      "_id" -> q._id,
      "id" -> q.id,
      "name" -> q.name,
      "description" -> q.description,
      "date" -> q.date,
      "answers" -> q.answers)
  }

  implicit object QuestionReader extends BSONDocumentReader[QuestionDAO] {
    def read(doc: BSONDocument): QuestionDAO = QuestionDAO(
      doc.getAs[BSONObjectID]("_id").get,
      doc.getAs[String]("id").get,
      doc.getAs[String]("name").get,
      doc.getAs[String]("description").get,
      doc.getAs[String]("date").get,
      doc.getAs[List[RespuestaDAO]]("answers").toList.flatten)
  }

}
