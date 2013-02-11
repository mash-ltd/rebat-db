package com.mashltd.rebatdb

object Rebat {
  def main(args: Array[String]) {
    Configuration.parseConfiguration()
    Server.start()
  }
}
