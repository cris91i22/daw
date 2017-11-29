package repository

import com.google.inject.ImplementedBy
import com.typesafe.config.ConfigFactory
import model.Vegetable
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID}
import repository.common.BaseRepository
import repository.dao.VegetableDAO

import scala.concurrent.Future

@ImplementedBy(classOf[VegetablesRepositoryImpl])
trait VegetablesRepository extends BaseRepository[VegetableDAO, Vegetable] {
  def insert(name: String, description: String, price: Double)(implicit b: BSONDocumentWriter[VegetableDAO]): Future[Vegetable]
}

class VegetablesRepositoryImpl extends VegetablesRepository {
  override val collectionName: String = ConfigFactory.load().getString("db.collections.verduras")

  override def upConvert(dao: VegetableDAO): Vegetable = {
    Vegetable(dao.id, dao.name, dao.description, dao.price)
  }

  def insert(name: String, description: String, price: Double)(implicit b: BSONDocumentWriter[VegetableDAO]): Future[Vegetable] = {
    val _id = BSONObjectID.generate
    val id = _id.stringify
    val dao = VegetableDAO(
      _id,
      id,
      name,
      description,
      price
    )

    this.create(dao)
  }
}