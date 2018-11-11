package ca.panagiotis.blog.search

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS

import scala.concurrent.ExecutionContext

object SearchServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

object ServerStream {

  def searchService[F[_]: Effect] = new SearchService[F].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext) = {
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(CORS(searchService), "/api/search")
      .serve
  }

}
