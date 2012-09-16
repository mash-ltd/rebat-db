package com.mashltd.rebatdb

import java.io.IOException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import com.mashltd.rebatdb.thrift.RebatDB;

object Server {
  def start() {
    try {
      val serverTransport:TServerSocket = new TServerSocket(Configuration.port);
      val processor:RebatDB.Processor[RebatDBImpl] = new RebatDB.Processor(new RebatDBImpl());
      val server:TServer = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      println("Listening on port %d ...".format(Configuration.port));
      server.serve();
    } catch {
      case e:TTransportException => e.printStackTrace()
      case e:IOException => e.printStackTrace();
    }
  }
}
