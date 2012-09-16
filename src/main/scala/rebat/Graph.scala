package com.mashltd.rebatdb

import java.util.List
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import scala.collection.JavaConversions._
import com.mashltd.rebatdb.thrift.QueryType

object Graph extends Schema {
  val edges = table[Edge]("Edges")

  on(edges) (ed => declare(
    columns(ed.from_entity_id, ed.from_entity_type, ed.relation_id) are(indexed),
    columns(ed.to_entity_id, ed.to_entity_type, ed.relation_id) are(indexed)
  ))

  def addEdge(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    try {
      inTransaction {
        edges.insert(Edge.fromThrift(edge))
      }
      return true
    } catch {
      case e:Exception => 
        println(e)
        return false
    }
  }

  def deleteEdge(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    try {
      inTransaction {
        edges.deleteWhere (ed => 
          (
            ed.from_entity_id   === edge.fromEntityId.inhibitWhen(!edge.isSetFromEntityId())   and
            ed.from_entity_type === edge.fromEntityType.inhibitWhen(!edge.isSetFromEntityType()) and
            ed.to_entity_id     === edge.toEntityId.inhibitWhen(!edge.isSetToEntityId())     and
            ed.to_entity_type   === edge.toEntityType.inhibitWhen(!edge.isSetToEntityType())   and
            ed.relation_id      === edge.relationId
          )
        )
        return true
      }
    } catch {
      case e:Exception => 
        println(e)
        return false
    }
  }

  def updateEdgeWeight(edge:com.mashltd.rebatdb.thrift.Edge, weight:Long):Boolean = {
    try {
      inTransaction {
        update(edges) (ed => 
          where(
            ed.from_entity_id === edge.fromEntityId and 
            ed.from_entity_type === edge.fromEntityType and 
            ed.to_entity_id === edge.toEntityId and
            ed.to_entity_type === edge.toEntityType and
            ed.relation_id === edge.relationId
          )

          set(ed.weight:= weight)
        )
        return true
      }
    } catch {
      case e:Exception => 
        println(e)
        return false
    }
  }

  def selectEdges(query_list:List[com.mashltd.rebatdb.thrift.Query]):List[com.mashltd.rebatdb.thrift.Edge] = {
    try {
      inTransaction {
        var select_query = from(edges)(ed => 
          select(ed)
          orderBy(ed.weight desc)
        )

        query_list.foreach(query => 
          query.qtype match {
            case QueryType.WHERE => 
              select_query = select_query.where(ed => 
                ed.from_entity_id   === query.edge.fromEntityId.inhibitWhen(!query.edge.isSetFromEntityId())   and
                ed.from_entity_type === query.edge.fromEntityType.inhibitWhen(!query.edge.isSetFromEntityType()) and
                ed.to_entity_id     === query.edge.toEntityId.inhibitWhen(!query.edge.isSetToEntityId())     and
                ed.to_entity_type   === query.edge.toEntityType.inhibitWhen(!query.edge.isSetToEntityType())   and
                ed.relation_id      === query.edge.relationId
              )
            case QueryType.UNION =>
              select_query = select_query.union(select_query.where(ed =>
                ed.from_entity_id   === query.edge.fromEntityId.?   and
                ed.from_entity_type === query.edge.fromEntityType.? and
                ed.to_entity_id     === query.edge.toEntityId.?     and
                ed.to_entity_type   === query.edge.toEntityType.?   and
                ed.relation_id      === query.edge.relationId
              ))
          }
        )    

        return select_query.toList.map[com.mashltd.rebatdb.thrift.Edge, scala.collection.immutable.List[com.mashltd.rebatdb.thrift.Edge]](ed => ed.toThrift)//.asInstanceOf[java.util.List[com.mashltd.rebatdb.thrift.Edge]]
      }
    } catch {
      case e:Exception => 
        println(e)
        return null
    }
  }
}