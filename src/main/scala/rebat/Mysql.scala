package com.mashltd.rebatdb

import java.sql.{Connection, DriverManager, ResultSet}
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._

object Mysql {
  private var _connection_string = ""
  private var _connection:Connection = null

  def initialize() {
    _connection_string = String.format("jdbc:mysql://%s/%s", Configuration.mysql_url, Configuration.mysql_dbname)

    connect()

    println("Checking schema...")
    if(!checkSchemaExistance){
      generateSchema()
    } else {
      println("Schema OK!")
    }
  }

  private def connect():Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    _connection = DriverManager.getConnection(_connection_string, Configuration.mysql_dbuser, Configuration.mysql_dbpassword)
    SessionFactory.concreteFactory = Some(() => Session.create(_connection, new MySQLAdapter))
  }

  private def generateSchema() {
    transaction {
      println("Creating schema...")
      Graph.create
      println("Schema created successfully")
    }
  }

  private def checkSchemaExistance:Boolean = {
    try {
      // Configure to be Read Only
      val statement = _connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      var rs = statement.executeQuery("SHOW TABLES LIKE 'Edges';")

      return rs.next.asInstanceOf[Boolean]
    }
    catch {
      case ex: Exception => {
        return false
      }
    }
  }
}