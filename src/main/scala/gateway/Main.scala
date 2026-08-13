package gateway

import gateway.pipeline.Step
import gateway.pipeline.http.{Context, PipeLine}
import gateway.pipeline.steps.{LoggerStep, MetricStep}
import zio.*
import zio.http.*

import java.lang.annotation.Target

object Main extends ZIOAppDefault{

  private def createToken : String = { "token" };

  val hostCache = ZIO.succeed(
    for {
      cache <- Ref.make(Set.empty[String])
    }yield cache
  )

  implicit class LocalToIpv4(str : String) {
    def toIpv4(): String = {
      if (str == "0:0:0:0:0:0:0:1") "127.0.0.1" else str
    }
  }

  case class AuthStep(hostCache : Ref[Set[String]]) extends Step {
    def proccess(ctx: Context): UIO[Context] = {
      val host_name = ctx.request.remoteAddress
        .map(_.getHostAddress.toIpv4())
        .getOrElse("unknown-host")
      for{
        token <- ZIO.succeed(createToken)
        cachedHosts <- hostCache.get
        isThere = cachedHosts.contains(host_name)
        _ <- if (!isThere) hostCache.update(_ + host_name) else ZIO.unit
        updated <- hostCache.get
        - <- ZIO.log(s"CACHE - ${updated.mkString(",")}")
      }yield ctx
    }
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for{
      cache <- Ref.make(Set.empty[String])
      routes = PipeLine.route(cache,LoggerStep >>> MetricStep)
      _ <- Server.serve(routes).provide(Server.defaultWithPort(8080))
    }yield()
  }
}
