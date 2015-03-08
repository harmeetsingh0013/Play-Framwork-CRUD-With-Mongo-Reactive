/**
 *
 */
package models

import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import java.util.Date

/**
 * @author james
 *
 */
case class UserDetail(
  val _id: String,
  val name: String,
  val age: Double,
  val created: Date
) 

object UserDetail{
  implicit val userDetailReads: Reads[UserDetail] = (
    (JsPath \ "_id").read[String] and
    (JsPath \ "name").read[String] and
    (JsPath \ "age").read[Double] and
    (JsPath \ "created").read[Date]
  )(UserDetail.apply _)
  
  implicit val userDetailWrites: Writes[UserDetail] = (
    (JsPath \ "_id").write[String] and
    (JsPath \ "name").write[String] and
    (JsPath \ "age").write[Double] and
    (JsPath \ "created").write[Date] 
  )(unlift { UserDetail.unapply }) 
}