play2-jsend
========================

play2-jsend is a small module for [Play Framework](https://www.playframework.com), which adds support for JSend.
>  JSend is a specification that lays down some rules for how JSON responses from web servers should be formatted.

You can find the full specification at [OmniTI Labs Wiki](http://labs.omniti.com/labs/jsend).

Installation
-------------------------

play2-jsend is [published in Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22play2-jsend_2.11%22). To use it you just need to add a dependency declaration into your build.sbt or Build.scala:

__build.sbt__

```scala
"me.mnedokushev" %% "play2-jsend" % "1.0.0"
```

__Build.scala__

```scala
val appDependencies = Seq(
  "me.mnedokushev" %% "play2-jsend" % "1.0.0"
)
```

Usage
-------------------------

Instead of using Json.toJson, use JSend.success/fail/error static methods. Here are some examples:

```scala

import play.api.libs.json.JSend

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
//      "status": "fail",
//      "data": { "userName": "userName is required", "password": "password is required" }
//    }


/**
* Error
*/

JSend.error("Something went wrong.")
// => {
//      "status": "error",
//      "message": "Something went wrong."
//    }


/**
* Error with error code
*/

JSend.error("Something went wrong.", 42)
// => {
//      "status": "error",
//      "message": "Something went wrong.",
//      "code": 42
//    }


/**
* Error with data
*/

JSend.error("Something went wrong.", "userName" -> "userName is required")
// => {
//      "status": "error",
//      "message": "Something went wrong.",
//      "data": { "userName": "userName is required" }
//    }


/**
* Error with error code and data
*/

JSend.error("Something went wrong.", 42, "userName" -> "userName is required")
// => {
//      "status": "error",
//      "message": "Something went wrong.",
//      "code": 42,
//      "data": { "userName": "userName is required" }
//    }

```
