package com.mashltd.rebatdb

import java.io._
import org.yaml.snakeyaml._
import java.util._

object Configuration {
  private var _mysql_url = ""
  private var _mysql_dbname = ""
  private var _mysql_dbuser = ""
  private var _mysql_dbpassword = ""

  def parseConfiguration() {
    val config_file = new FileInputStream(new File("config/rebat.yml"))
    
    val yaml = new Yaml()
    val conf:LinkedHashMap[String, String] = yaml.load(config_file).asInstanceOf[LinkedHashMap[String, String]].get("development").asInstanceOf[LinkedHashMap[String, String]]

    _mysql_url = conf.get("mysql_url")
    _mysql_dbname = conf.get("mysql_dbname")
    _mysql_dbuser = conf.get("mysql_dbuser")
    _mysql_dbpassword = conf.get("mysql_dbpassword")

    Mysql.initialize()
  }

  def  mysql_url:String = {
    return _mysql_url
  }

  def mysql_dbname:String = {
    return _mysql_dbname
  }

  def mysql_dbuser:String = {
    return _mysql_dbuser
  }

  def mysql_dbpassword:String = {
    return _mysql_dbpassword
  }
}