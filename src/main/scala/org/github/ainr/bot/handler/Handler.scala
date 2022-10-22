package org.github.ainr.bot.handler

import cats.effect.IO
import org.github.ainr.bot.reaction.{Reaction, SendText, Sleep}
import org.github.ainr.infrastructure.logger.CustomizedLogger
import telegramium.bots.{ChatIntId, Message}

import scala.concurrent.duration.DurationInt

trait Handler {
  def handle(message: Message): IO[List[Reaction]]
}

object Handler {

  def apply(logger: CustomizedLogger[IO]): Handler = new Handler {

    override def handle(message: Message): IO[List[Reaction]] = {
      IO.delay {
        List(
          SendText(
            ChatIntId(message.chat.id),
            message.text.getOrElse("Echo")
          ),
          Sleep(1 second),
          SendText(
            ChatIntId(message.chat.id),
            message.text.getOrElse("Echo")
          )
        )
      } <* logger.info(s"Message from ${message.chat.id}")
    }
  }
}
