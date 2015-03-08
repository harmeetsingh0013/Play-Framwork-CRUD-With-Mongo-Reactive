/**
 *
 */
package controllers

import play.modules.reactivemongo.MongoController
import play.api.mvc.Controller
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.FailoverStrategy
import play.api.mvc.Action
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import models.UserDetail
import reactivemongo.api.Cursor
import play.api.libs.json.JsObject
import play.api.libs.json.JsObject
import scala.collection.immutable.List
import scala.concurrent.impl.Future
import scala.concurrent.Future
import play.api.libs.json.JsObject
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument

/**
 * @author Harmeet Singh(Taara)
 * @version 1.0
 */
object MongoDBSampleController extends Controller with MongoController{
  
  def collection : JSONCollection = db.collection[JSONCollection]("users", FailoverStrategy());
  
  def create(name: String, age: Int) = Action.async {
    val json = Json.obj(
      "name" -> name,
      "age" -> age,
      "created" -> new java.util.Date().getTime())

    collection.insert(json).map(lastError =>
      if(lastError.ok){
        Ok("DONE")
      }else{
        InternalServerError
      }
     )
  }
  
  def createFromJson = Action(parse.json){ implicit request => 
   val userDetailResult = request.body.validate[UserDetail];
   userDetailResult.fold(
     errors => {
       BadRequest
     }, 
     userDetail => {
        collection.insert(userDetail).map { lastError =>  
          if(lastError.ok){
            Ok("DONE")
          }else{
            InternalServerError
          }
        }
      })
    Ok("DONE")
  }
  
  def findUserByName(name: String) = Action.async {
    
    val cursor: Cursor[JsObject] = collection.find(Json.obj("name" -> name)).
                                    sort(Json.obj("created" -> -1)).cursor[JsObject];
    val usersList : Future[List[JsObject]] = cursor.collect[List](10, true);
    usersList.map { users => Ok(Json.toJson(users)) } 
  }
  
  def findAllUsers = Action.async {
    
    val cursor: Cursor[JsObject] = collection.find(Json.obj()).sort(Json.obj("created" -> -1)).cursor[JsObject];
    val usersList : Future[List[JsObject]] = cursor.collect[List](10, true);
    usersList.map { users => Ok(Json.toJson(users)) } 
  }
  
  def updateUserDetail = Action(parse.json){ implicit request =>
    val userDetailResult = request.body.validate[UserDetail];
   userDetailResult.fold(
     errors => {
       BadRequest
     }, 
     userDetail => {
        println("In User Detail")
        collection.update(Json.obj("_id" -> "54fc6612d3c1243299a689da"), Json.obj("$set" -> parse.json.toString())).map { lastError =>  
          if(lastError.ok){
            Ok("DONE")
          }else{
            InternalServerError
          }
        }
      })
    Ok("DONE")
  }
}