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
      
      (json \ "status").get must be equalTo(JsString("success"))
      (json \ "data" \ "users").as[JsArray].value must have size(2)
      
    }
    
    "return JsObject with two keys, and 'status' = 'success'" in {
      val json = JSend.success("user1" -> User(1, "Bob", 40), "user2" -> User(2, "Alice", 33))
      
      (json \ "status").get must be equalTo(JsString("success"))
      (json \ "data").as[JsObject].keys must have size(2)
    }
  }

  "JSend.fail" should {
    "return list of users inside 'data', and 'status' = 'fail'" in {
      val json = JSend.fail("users" -> List(User(1, "Bob", 40)))
      
      (json \ "status").get must be equalTo(JsString("fail"))
      (json \ "data" \ "users").as[JsArray].value must have size(1)
    }
    
    "return JsObject for 'data' with two keys, and 'status' = 'fail'" in {
      val json = JSend.fail("title" -> "title is required", "password" -> "password is required")
      
      (json \ "status").get must be equalTo(JsString("fail"))
      (json \ "data").as[JsObject].keys must have size(2)
    }
  }

  "JSend.error" should {
    "return 'status' = 'error' with a companion message, but contain no data" in {
      val msg = "test message"
      val json = JSend.error(msg)

      (json \ "status").get must be equalTo(JsString("error"))
      (json \ "message").get must be equalTo(JsString(msg))
      (json \ "data").asOpt[JsObject] must beNone
    }
    
    "all the above plus contain an integer code" in {
      val msg = "test message"
      val code = 42
      val json = JSend.error(msg, code)
      
      (json \ "status").get must be equalTo(JsString("error"))
      (json \ "message").get must be equalTo(JsString(msg))
      (json \ "code").get must be equalTo(JsNumber(code))
      (json \ "data").asOpt[JsObject] must beNone
    }

    "return 'status' = 'error', error message, no integer code but contain some data" in {
      val msg = "test message"
      val json = JSend.error(msg, "foo" -> User(1, "Foo", 3))
      
      (json \ "status").get must be equalTo(JsString("error"))
      (json \ "message").get must be equalTo(JsString(msg))
      (json \ "code").asOpt[JsObject] must beNone
      (json \ "data").asOpt[JsObject] must beSome
    }
    
    "all the above plus contain an integer code" in {
      val msg = "test message"
      val code = 42
      val json = JSend.error(msg, code, "foo" -> User(1, "Foo", 3))
      
      (json \ "status").get must be equalTo(JsString("error"))
      (json \ "message").get must be equalTo(JsString(msg))
      (json \ "code").get must be equalTo(JsNumber(code))
      (json \ "data").asOpt[JsObject] must beSome
    }
  }

}
