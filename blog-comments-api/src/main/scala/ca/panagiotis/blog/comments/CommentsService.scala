package ca.panagiotis.blog.comments

import cats.effect.Effect
import io.circe.Json
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class CommentsService[F[_]: Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / comment =>
        Ok(Json.obj("results" -> Json.fromString(s"Comment: ${comment}")))
    }
  }
}
