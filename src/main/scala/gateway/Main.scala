package gateway

import gateway.pipeline.Step
import gateway.pipeline.http.{Context, PipeLine}
import gateway.pipeline.steps.{AuthStep, LoggerStep, MetricStep}
import zio.*
import zio.http.*

object Main extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for{
      cache <- Ref.make(Set.empty[String])
      routes = PipeLine.route(cache,LoggerStep >>> AuthStep  >>> MetricStep)
      _ <- Server.serve(routes).provide(Server.defaultWithPort(8080))
    }yield()
  }
}
