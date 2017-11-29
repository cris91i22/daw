package repository.dao

import reactivemongo.bson.BSONObjectID

trait Entity {
  //  @Key("_id")
  val _id: BSONObjectID
}

case class VegetableDAO(_id: BSONObjectID,
                        id: String,
                        name: String,
                        description: String,
                        price: Double) extends Entity
