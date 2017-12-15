package repository

import com.google.inject.ImplementedBy
import com.typesafe.config.ConfigFactory
import model.{Contact, Respuesta}
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID}
import repository.common.BaseRepository
import repository.dao.{ContactDAO, RespuestaDAO}

import scala.concurrent.Future

@ImplementedBy(classOf[ContactRepositoryImpl])
trait ContactRepository extends BaseRepository[ContactDAO, Contact] {
  def insert(bolsonId: String, name: String, email: String, contactNumber: String, delivery: Boolean)(implicit b: BSONDocumentWriter[ContactDAO]): Future[Contact]
}

class ContactRepositoryImpl extends ContactRepository {
  override val collectionName: String = ConfigFactory.load().getString("db.collections.contactos")

  override def upConvert(dao: ContactDAO): Contact = {
    Contact(dao.bolsonId, dao.name, dao.email, dao.contactNumber, dao.delivery)
  }

  def insert(bolsonId: String, name: String, email: String, contactNumber: String, delivery: Boolean)(implicit b: BSONDocumentWriter[ContactDAO]) = {
    def toRespuestaDAO(r: Respuesta): RespuestaDAO = {
      val _id = BSONObjectID.generate
      val id = _id.stringify
      RespuestaDAO(_id, id, r.description, r.user, r.date)
    }

    val _id = BSONObjectID.generate
    val id = _id.stringify
    val dao = ContactDAO(
      _id,
      id,
      bolsonId,
      name,
      email,
      contactNumber,
      delivery
    )

    this.create(dao)
  }

}
