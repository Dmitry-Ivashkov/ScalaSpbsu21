package org.spbsu.scala

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.cats.{Polling, TelegramBot}
import org.scalatest.funsuite.AnyFunSuite
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend
import sttp.client3.{SttpBackend, UriContext, asFile, basicRequest}
//import com.bot4s.telegram.future.{Polling, TelegramBot}
//import com.bot4s.telegram.future.TelegramBot
import java.io.FileInputStream

class TelegramTest extends AnyFunSuite {

  val token: String = new String("1799231103:AAHRXMReArGosUaxDmeUUYQb0alG2y9uTBs")

  class MyTGBot(implicit val backend : SttpBackend[IO, Any]) extends TelegramBot[IO](token, backend)
  with Polling[IO]
  with Commands[IO] {
    onCommand("/ping"){
      implicit msg =>
        reply("pong")
          .map(println(_))
    }
    onCommand("/exit"){
      implicit msg =>
        reply("pong")
          .map(println(_))
    }
     onMessage{implicit msg =>
       msg.photo.map(_.map(_.fileUniqueId))
       reply(msg.text.getOrElse("?")).void
     }
  }

  test("send TG message") {
    val value = AsyncHttpClientCatsBackend[IO]().flatMap {implicit backend =>
      val bot = new MyTGBot()
      bot.run()
    }.unsafeRunSync()
  }

}
