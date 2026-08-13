package gateway.pipeline.steps

import gateway.pipeline.Step
import gateway.pipeline.http.Context
import zio.{UIO, ZIO}

object LoggerStep extends Step {
  def proccess(ctx: Context): UIO[Context] =
    ZIO.log("hello").map(_ => ctx)
}
