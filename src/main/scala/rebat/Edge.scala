package com.mashltd.rebatdb

import org.squeryl.PrimitiveTypeMode._

class Edge (val from_entity_id: Long, 
            val from_entity_type: String,
            val to_entity_id: Long,
            val to_entity_type: String,
            val weight: Long,
            val relation_id: Long) {

  def this() = this(0, "", 0, "", 0, 0)   

  def toThrift:com.mashltd.rebatdb.thrift.Edge = {
    return null
  }
}

object Edge {
  def fromThrift(thriftEdge:com.mashltd.rebatdb.thrift.Edge):Edge = {
    return null
  }
}