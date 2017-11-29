package repository.handler

import reactivemongo.bson.{DefaultBSONHandlers, Macros}
import repository.dao.VegetableDAO

object BSONDocumentHandlers extends DefaultBSONHandlers {

  implicit val vegetableHandler = Macros.handler[VegetableDAO]

}
