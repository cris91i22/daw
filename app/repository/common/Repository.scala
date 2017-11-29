package repository.common

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{MongoConnection, MongoDriver}
import repository.config.DbConfig
import repository.dao.Entity

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Repository[T <: Entity] extends DbConfig {

  val collectionName: String

  val driver = new MongoDriver
  val database = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    con = driver.connection(uri)
    dn <- Future(uri.db.get)
    db <- con.database(dn)
  } yield db

  val collection = database.map(d => d[BSONCollection](collectionName))

}
