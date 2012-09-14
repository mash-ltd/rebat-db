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
    val thriftEdge = new com.mashltd.rebatdb.thrift.Edge()
    thriftEdge.setFromEntityId(from_entity_id)
    thriftEdge.setFromEntityType(from_entity_type)
    thriftEdge.setToEntityId(to_entity_id)
    thriftEdge.setToEntityType(to_entity_type)
    thriftEdge.setWeight(weight)
    thriftEdge.setRelationId(relation_id)

    return thriftEdge
  }
}

object Edge {
  def fromThrift(thriftEdge:com.mashltd.rebatdb.thrift.Edge):Edge = {
    return new Edge(thriftEdge.fromEntityId, 
                    thriftEdge.fromEntityType,
                    thriftEdge.toEntityId,
                    thriftEdge.toEntityType,
                    thriftEdge.weight,
                    thriftEdge.relationId
    )
  }
}