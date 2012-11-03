package com.mashltd.rebatdb

import java.sql.{Connection, DriverManager, ResultSet}
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._

object Mysql {
  private var _connection_string = ""

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

  def truncateTable:Boolean = {
    try {
      // Configure to be Read Only
      transaction {
        val statement = Session.currentSession.connection.createStatement()

        statement.executeQuery("TRUNCATE TABLE edges;")

        return true
      }
    }
    catch {
      case ex: Exception => {
        println(ex)
        return false
      }
    }
  }

  private def connect():Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(DriverManager.getConnection(_connection_string, Configuration.mysql_dbuser, Configuration.mysql_dbpassword), new MySQLAdapter))
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
      transaction {
        val statement = Session.currentSession.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

        var rs = statement.executeQuery("SHOW TABLES LIKE 'Edges';")

        return rs.next.asInstanceOf[Boolean]
      }
    }
    catch {
      case ex: Exception => {
        return false
      }
    }
  }
}