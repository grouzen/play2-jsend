play2-jsend
========================

It's a small module for Play2 framework, which adds support for JSend specification.
More information about JSend you can find [here](http://labs.omniti.com/labs/jsend/wiki).

Installation
-------------------------

Add dependency declarations into your build.sbt or Build.scala file:

* ___build.sbt___

        "me.mnedokushev" %% "play2-jsend" % "1.0-SNAPSHOT"

* ___Build.scala___

```scala
  val appDependencies = Seq(
    "me.mnedokusev" %% "play2-jsend" % "1.0-SNAPSHOT"
  )
```

Usage
-------------------------

Instead of using Json.toJson, use JSend.success/fail/error static methods. 

```scala

  // Examples
  
  import play.api.libs.json._

  /** 
   * Success
   */
   
  case class User(id: Long, name: String)

  JSend.success("users" -> List(User(1, "Bob"), User(2, "Alice")))
  // => { 
  //      "status": "success", 
  //      "data": { "users": [{"id": 1, "name": "Bob"}, {"id": 2, "name": "Alice"}]}
  //    }
  
  /**
   * Fail
   */
   
   JSend.fail("userName" -> "userName is required", "password" -> "password is required")
   // => {
   //      "status": "fail"
   //      "data": { "userName": "userName is required", "password": "password is required" }
   //    }

```
