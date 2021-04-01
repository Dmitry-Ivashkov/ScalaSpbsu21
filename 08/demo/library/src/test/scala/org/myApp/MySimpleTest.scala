package org.myApp

import org.scalatest.funsuite.AnyFunSuite

class MySimpleTest extends AnyFunSuite{
  test("first"){
    assertResult(4)(MyLibraryProvider.sqr(2))
  }
}
