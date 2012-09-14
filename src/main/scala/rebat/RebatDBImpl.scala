package com.mashltd.rebatdb

import java.util.List
import org.apache.thrift.TException

class RebatDBImpl extends com.mashltd.rebatdb.thrift.RebatDB.Iface {
  def addQuery(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    return Graph.addEdge(edge)
  }

  def deleteQuery(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    return Graph.deleteEdge(edge)
  }

  def updateWeightQuery(edge:com.mashltd.rebatdb.thrift.Edge, weight:Long):Boolean = {
    return Graph.updateEdgeWeight(edge, weight)
  }

  def selectQuery(query_list:List[com.mashltd.rebatdb.thrift.Query]):List[com.mashltd.rebatdb.thrift.Edge] = {
    return Graph.selectEdges(query_list)
  }
}