package play.api.libs.json

object JSend {

  private def wrap[T](status: String, objs: Option[Seq[(String, T)]] = None)(implicit tjs: Writes[T]): JsObject = {
    Json.obj("status" -> status) ++
      (objs match {
        case None => Json.obj()
        case Some(Seq(xs@_*)) =>
          Json.obj("data" -> 
                     xs 
                       .map { o => Json.obj(o._1 -> o._2) } 
                       .foldLeft(Json.obj()) { 
                          (a, b) => 
                            def mkJson(o: JsObject): JsObject = {
                              o.keys.headOption match {
                                case Some(k) => Json.obj(k -> Json.toJson(o.values.head))
                                case None    => Json.obj()
                              }
                            }
          
                            mkJson(a) ++ mkJson(b)
                        }
                 )
    })  
  }

  def success[T](objs: (String, T)*)(implicit tjs: Writes[T]): JsValue = 
    wrap(status="success", objs=Some(objs))
    
  def fail[T](objs: (String, T)*)(implicit tjs: Writes[T]): JsValue =
    wrap(status="fail", objs=Some(objs))
  
  def error[T](message: String, code: Option[Int] = None, objs: Option[Seq[(String, T)]] = None)(implicit tjs: Writes[T]): JsValue =
    Json.obj("message" -> message) ++ 
      wrap(status="error", objs=objs) ++
      (code match {
        case Some(c) => Json.obj("code" -> c)
        case None    => Json.obj()
      })

}
