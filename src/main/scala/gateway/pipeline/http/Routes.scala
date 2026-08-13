package gateway.pipeline.http

import gateway.pipeline.Step
import gateway.pipeline.steps.{LoggerStep, MetricStep}
import zio.Ref
import zio.http.*

case class Context(request: Request, response: Option[Response], stop : Boolean)

object PipeLine {

  def runPipeLine(request: Request, pipeLine: Step) = {
    pipeLine.proccess(Context(request, None, false))
      .map(_.response.getOrElse(Response.text("error")))
  }

  def route(hostCache: Ref[Set[String]], pipeLine: Step) =
    Routes(
      Method.ANY / trailing -> handler {
        (request: Request) => {
          runPipeLine(request, pipeLine)
        }
      }
    )
}