package gateway.pipeline.steps

import gateway.pipeline.Step
import gateway.pipeline.http.Context
import zio.{UIO, ZIO}

object MetricStep extends Step {
  def proccess(ctx: Context): UIO[Context] = for {
    _ <- ZIO.log("metrics")
  }yield ctx;
}
