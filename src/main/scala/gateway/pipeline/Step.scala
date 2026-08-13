package gateway.pipeline

import gateway.pipeline.http.Context
import zio.{UIO, ZIO}

trait Step {
  def proccess(ctx : Context) : UIO[Context]
  def >>>(next: Step) : Step = (ctx : Context ) => {
    this.proccess(ctx).flatMap(c => if (c.stop) ZIO.succeed(c) else next.proccess(c))
  }
}
