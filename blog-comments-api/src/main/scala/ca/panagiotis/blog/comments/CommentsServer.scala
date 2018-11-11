package ca.panagiotis.blog.comments

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object CommentsServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

object ServerStream {

  def commentsService[F[_]: Effect] = new CommentsService[F].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext) =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(commentsService, "/api/comments")
      .serve
}
