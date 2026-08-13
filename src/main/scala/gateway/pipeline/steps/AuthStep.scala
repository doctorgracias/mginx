package gateway.pipeline.steps

import gateway.pipeline.Step
import gateway.pipeline.http.Context
import zio.http.Response
import zio.{Ref, UIO, ZIO}

object AuthStep extends Step {
  def proccess(ctx: Context): UIO[Context] =
    val newCtx = Context(ctx.request,response = Some(Response.text("ok")),false)
    for {
    _ <- ZIO.log("auth")
  }yield newCtx;
}

//private def createToken: String = {
//  "token"
//};
//
//val hostCache = ZIO.succeed(
//  for {
//    cache <- Ref.make(Set.empty[String])
//  } yield cache
//)
//
//case class SAuthStep(hostCache: Ref[Set[String]]) extends Step {
//  def proccess(ctx: Context): UIO[Context] = {
//    val host_name = ctx
//      .request
//      .remoteAddress
//      .getOrElse("unknown-host").toString
//    for {
//      token <- ZIO.succeed(createToken)
//      cachedHosts <- hostCache.get
//      isThere = cachedHosts.contains(host_name)
//      _ <- if (!isThere) hostCache.update(_ + host_name) else ZIO.unit
//      updated <- hostCache.get
//      - <- ZIO.log(s"CACHE - ${updated.mkString(",")}")
//    } yield ctx
//  }
//}
