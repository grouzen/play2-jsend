package play.api.libs.json

import org.specs2.mutable._
import org.specs2.runner._

class JSendSpec extends Specification {

  case class User(id: Long, name: String, age: Int)
  object User {
    implicit val format = Json.format[User]
  }

  "JSend.success" should {
    "return list of users inside 'data', and 'status' = 'success'" in {
      val json = JSend.success("users" -> List(User(1, "Bob", 40), User(2, "Alice", 33)))
      
      (json \ "status") must be equalTo(JsString("success"))
      (json \ "data" \ "users").as[JsArray].value must have size(2)
      
    }
    
    "return JsObject with two keys, and 'status' = 'success'" in {
      val json = JSend.success("user1" -> User(1, "Bob", 40), "user2" -> User(2, "Alice", 33))
      
      (json \ "status") must be equalTo(JsString("success"))
      (json \ "data").as[JsObject].keys must have size(2)
    }
  }

  "JSend.fail" should {
    "return list of users inside 'data', and 'status' = 'fail'" in {
      val json = JSend.fail("users" -> List(User(1, "Bob", 40)))
      
      (json \ "status") must be equalTo(JsString("fail"))
      (json \ "data" \ "users").as[JsArray].value must have size(1)
    }
    
    "return JsObject for 'data' with two keys, and 'status' = 'fail'" in {
      val json = JSend.fail("title" -> "title is required", "password" -> "password is required")
      
      (json \ "status") must be equalTo(JsString("fail"))
      (json \ "data").as[JsObject].keys must have size(2)
    }
  }

  "JSend.error" should {
    "return 'status' = 'error'" in {
      val json = JSend.error(message="user foo", objs=Some(Seq("foo" -> User(1, "Foo", 3))))
      
      (json \ "status") must be equalTo(JsString("error"))
    }

    "contain some data" in {
      val json = JSend.error(message="test message", objs=Some(Seq("foo" -> User(1, "Foo", 3))))
      
      (json \ "data").asOpt[JsObject] must beSome
    }
    
    // FIX: doesn't work without objs=Some(_)
    // "contain no data" in {
    //   val json = JSend.error(message="test message")

    //   (json \ "data").asOpt[JsObject] must beNone
    // }
  }

}
