package ca.panagiotis.blog.search

import ca.panagiotis.blog.search.models.{SearchResponse, SearchResult}
import cats.effect.Effect
import io.circe.Json
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import io.circe.generic.auto._
import io.circe.syntax._

class SearchService[F[_]: Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / query =>
        Ok(SearchResponse(
          List(
            SearchResult(s"$query 1"), SearchResult(s"$query 2"), SearchResult(s"$query 3")
          )).asJson)
    }
  }
}
