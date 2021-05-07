package org.spbsu.scala

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.component.VEvent
import org.scalatest.funsuite.AnyFunSuite

import java.io.FileInputStream
import net.fortuna.ical4j.util.MapTimeZoneCache
import org.scalatest.BeforeAndAfter

import scala.collection.JavaConverters._

class IcalTest extends AnyFunSuite with BeforeAndAfter {

  before {
    System.setProperty("net.fortuna.ical4j.timezone.cache.impl", classOf[MapTimeZoneCache].getName)
    System.setProperty("ical4j.parsing.relaxed", "true")
  }

  implicit class FISExt(is: FileInputStream) {
    def filteringDSTAMP: PrefixFilterInputStream = new PrefixFilterInputStream(is) {
      override def excludeLine(line: String): Boolean = line.startsWith("DTSTAMP")
    }
  }

  test("read ical from file") {
    val iStream = new FileInputStream("classes.ics").filteringDSTAMP
    val calendar = new CalendarBuilder().build(iStream)
    val events = calendar
      .getComponents
      .getAll
      .asScala
      .collect{case ev :VEvent => ev}
//    events.map(_.getProperties.get())
  }

  test("read ical from stream over http") {

  }
}
