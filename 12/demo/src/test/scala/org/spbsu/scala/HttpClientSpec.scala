package org.spbsu.scala

import org.scalatest.funsuite.AnyFunSuite
import sttp.client3._
import cats.effect.IO
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import cats.effect.unsafe.implicits.global

import java.io.File
import cats.effect.IO
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

class HttpClientSpec extends AnyFunSuite with Matchers{
  test("http request to ya.ru returns content") {
    val value = AsyncHttpClientCatsBackend[IO]().flatMap { backend =>
      basicRequest
        .get(uri"https://httpbin.org/get")
        .send(backend)
    }.map((resp => print(resp.body)))
      //...
    value.unsafeRunSync()
  }

  test("download ical from emkn") {
    val responsefile = new File("classes.ics")
    val value = AsyncHttpClientCatsBackend[IO]().flatMap { backend =>
      basicRequest
        .response(asFile(responsefile))
        .get(uri"https://emkn.ru/users/163/classes.ics")
        .send(backend)
    }
    //...
    value.unsafeRunSync()
    responsefile.exists() shouldBe true
//    responsefile.length() should be > 0

  }

}
