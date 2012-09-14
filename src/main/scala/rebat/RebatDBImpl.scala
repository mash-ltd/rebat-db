package com.mashltd.rebatdb

import java.util.List
import org.apache.thrift.TException

class RebatDBImpl extends com.mashltd.rebatdb.thrift.RebatDB.Iface {
  def addQuery(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    return false
  }

  def removeQuery(edge:com.mashltd.rebatdb.thrift.Edge):Boolean = {
    return false
  }

  def updateWeightQuery(edge:com.mashltd.rebatdb.thrift.Edge, weight:Long):Boolean = {
    return false
  }

  def whereQuery(edge:com.mashltd.rebatdb.thrift.Edge):List[com.mashltd.rebatdb.thrift.Edge] = {
    return null
  }

  def intersectQuery(edge:com.mashltd.rebatdb.thrift.Edge):List[com.mashltd.rebatdb.thrift.Edge] = {
    return null
  }

  def notQuery(edge:com.mashltd.rebatdb.thrift.Edge):List[com.mashltd.rebatdb.thrift.Edge] = {
    return null
  }

  def unionQuery(edge:com.mashltd.rebatdb.thrift.Edge):List[com.mashltd.rebatdb.thrift.Edge] = {
    return null
  }
}