package org.myApp


object MyMainApp{
  def MyNamber = 42

  def main(args: Array[String]): Unit = {
    print(MyLibraryProvider.sqr(MyNamber))
  }
}