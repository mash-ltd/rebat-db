package com.mashltd.rebatdb

import java.sql.{Connection, DriverManager, ResultSet}

object Mysql {
  private var _connection_string = ""
  private var _connection:Connection = null

  def initialize() {
    _connection_string = String.format("jdbc:mysql://%s/%s?user=%s&password=%s", Configuration.mysql_url, Configuration.mysql_dbname, Configuration.mysql_dbuser, Configuration.mysql_dbpassword)

    connect()

    initTables()
  }

  def readQuery(sql_query:String) : ResultSet = {
    if(_connection.isClosed) {
      connect()
    }

    try {
      // Configure to be Read Only
      val statement = _connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      return statement.executeQuery(sql_query)
    }
    catch {
      case ex: Exception => {
        close()
        println(ex)
        return null
      }
    }
  }

  def writeQuery(sql_query:String) = {
    if(_connection.isClosed) {
      connect()
    }

    try {
      // Configure to be Read Only
      val statement = _connection.createStatement()

      statement.executeUpdate(sql_query)
    }
    catch {
      case ex: Exception => {
        close()
        println(ex)
      }
    }
  }

  def close() {
    _connection.close
  }

  private def connect() {
    Class.forName("com.mysql.jdbc.Driver")
    _connection = DriverManager.getConnection(_connection_string)
  }

  private def initTables() {
    val rs = writeQuery("CREATE TABLE IF NOT EXISTS edges (from_entity_id INT NOT NULL, from_entity_type VARCHAR(255) NOT NULL, to_entity_id INT NOT NULL, to_entity_type VARCHAR(255) NOT NULL, weight INT NOT NULL, relation_id INT NOT NULL, INDEX from_entity (from_entity_id, from_entity_type, relation_id), INDEX to_entity (to_entity_id, to_entity_type, relation_id));")
  }
}