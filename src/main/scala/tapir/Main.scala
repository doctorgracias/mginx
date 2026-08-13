import zio.*
import zio.http.Server
import sttp.tapir.*
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir.ZServerEndpoint
import sttp.tapir.ztapir.RichZEndpoint

object Main extends ZIOAppDefault {
  val helloEndpoint: ZServerEndpoint[Any, Any] =
    endpoint.get
      .in("hello")
      .in(query[String]("name"))
      .out(stringBody)
      .zServerLogic { name =>
        ZIO.succeed(s"Hello, $name!")
      }

  val routes = ZioHttpInterpreter().toHttp(List(helloEndpoint))

  override def run =
    Server.serve(routes).provide(Server.default)
}