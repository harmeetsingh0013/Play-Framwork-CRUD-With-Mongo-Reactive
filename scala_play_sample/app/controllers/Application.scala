package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import models.UserDetail
import play.api.libs.json.Reads
import models.UserDetail
import play.api.libs.json.JsPath
import models.UserDetail
import play.api.libs.json.JsSuccess
import play.api.db.DB
import java.sql.Statement
import play.api.Play.current
import anorm.{SQL}
import anorm.SqlQuery
import anorm.Row
import scala.collection.immutable.List
import play.modules.reactivemongo.MongoController
import java.util.Date

object Application extends Controller{

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  /* This is the controller for write JSON in Response and fetch data from database*/
  def getUserDetail = Action{
    val userDetail = UserDetail("9874586", "James", 24.5, new Date());
    var sql: SqlQuery = SQL("SELECT * FROM USER_DETAIL");
    /*def users: List[UserDetail] =  DB.withConnection { implicit connection => 
      sql().map(row => UserDetail(row[Int]("id"), row[String]("name"), row[Double]("age"))).toList
    }*/
    Ok(Json.toJson(userDetail));
  }
  
  /* This is the controller for read JSON in request and Save the detail using simple JDBC*/
  def addUserDetail = Action (parse.json) { implicit request => 
   val userDetailResult = request.body.validate[UserDetail];
   userDetailResult.fold(
     errors => {
       BadRequest;
     }, 
      userDetail => {
        /*DB.withTransaction { implicit connection => 
         var id: Option[Long] = SQL("INSERT INTO USER_DETAIL (name, age) VALUES ({name}, {age})")
         .on('name -> userDetail.name, 'age -> userDetail.age).executeInsert()
         Ok(Json.obj("id" -> id.get))
        }*/
        Ok("DONE")
      })
  }

}