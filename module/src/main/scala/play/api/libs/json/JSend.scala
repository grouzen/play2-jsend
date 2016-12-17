package play.api.libs.json

import play.api.Logger

object JSend {

  private def statusToJson(status: String): JsObject =
    Json.obj("status" -> status)

  private def seqToJson[T](objs: Seq[(String, T)])(implicit tjs: Writes[T]): JsObject =
    Json.obj("data" ->
      objs
      .map { o => Json.obj(o._1 -> o._2) }
      .foldLeft(Json.obj()) {
        (a, b) => a ++ Json.obj(b.keys.head -> Json.toJson(b.values.head))
      })

  private def errorAux(message: String): JsObject =
    statusToJson("error") ++ Json.obj("message" -> message)

  def success[T](objs: (String, T)*)(implicit tjs: Writes[T]): JsValue =
    statusToJson("success") ++ seqToJson(objs)

  def fail[T](objs: (String, T)*)(implicit tjs: Writes[T]): JsValue =
    statusToJson("fail") ++ seqToJson(objs)

  def error(message: String): JsValue =
    errorAux(message)

  def error(message: String, code: Int): JsValue =
    errorAux(message) ++ Json.obj("code" -> code)

  def error[T](message: String, objs: (String, T)*)(implicit tjs: Writes[T]): JsValue =
    errorAux(message) ++ seqToJson(objs)

  def error[T](message: String, code: Int, objs: (String, T)*)(implicit tjs: Writes[T]): JsValue =
    errorAux(message) ++ Json.obj("code" -> code) ++ seqToJson(objs)

}
