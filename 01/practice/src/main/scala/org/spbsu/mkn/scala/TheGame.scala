package org.spbsu.mkn.scala

import scala.::
import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.io.StdIn.readLine
import scala.util.Random._

object TheGame {

  sealed trait GuessResult

  case class Correct(numTries: Int) extends GuessResult

  case class Incorrect(bulls: Int, cows: Int) extends GuessResult

  class RepeatingDigitsException extends RuntimeException

  class WrongNumberLengthException(expected: Int, got: Int) extends RuntimeException

  //  def generateNumberString(length: Int): String = alphanumeric.drop(length).toString()  // если эксклюзивность не важна
  def generateNumberString(length: Int): String = { // a-z A-Z 0-9
    if (length < 1) return ""
    val some_string = generateNumberString(length - 1)
    val c = alphanumeric.find(char => !some_string.contains(char)).get
    some_string + c
  }

  def validate(secret: String, userInput: String, numTries: Int = 1): GuessResult = {
    if (secret.toSet.size != secret.size) throw new RepeatingDigitsException
    if (userInput.toSet.size != userInput.size) throw new RepeatingDigitsException
    if (userInput.size != secret.size) throw new WrongNumberLengthException(secret.size, userInput.size)

    val bulls = userInput.zip(secret).count(pair => pair._1 == pair._2)
    val cows = userInput.toSet.intersect(secret.toSet).size - bulls
    if (bulls == secret.size) return Correct(numTries)
    Incorrect(bulls, cows)
  }

  def main(args: Array[String]): Unit = {
    println("введите длинну строки (<64)")
    var length = readLine().toInt
    println("будут использованны такие символы: a-z A-Z 0-9")
    var secret = generateNumberString(length)
    var guessResult: GuessResult = Incorrect(0, 0)
    var numTries = 0
    while (guessResult.isInstanceOf[TheGame.Incorrect]) {
      numTries += 1
      println(s"введите строку с уникалюными символами длинны $length")
      var userInput = readLine()
      try {
        guessResult = validate(secret, userInput, numTries)
        guessResult match {
          case Incorrect(bulls, cows) => {
            println(s"$bulls быков, $cows коров")
          }
          case Correct(numTries) =>
            println(s"верно за $numTries попыток")
          }
        }
      catch {
        case e: RepeatingDigitsException => println("встречен не уникальный символ")
        case e: WrongNumberLengthException => println("длинна вашей строки отличается")
        case e => throw e
      }
    }
  }
}
