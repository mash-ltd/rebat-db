namespace java com.mashltd.rebatdb.thrift

struct Edge {
  1: optional i64     fromEntityId; 
  2: optional string  fromEntityType; 
  3: optional i64     toEntityId; 
  4: optional string  toEntityType; 
  5: optional i64     weight = 0;
  6: optional i64     relationId;
}

service RebatDB {
  bool        addQuery(1:Edge edge);
  bool        removeQuery(1:Edge edge);
  bool        updateWeightQuery(1:Edge edge, 2:i64 weight);
  list<Edge>  whereQuery(1:Edge edge);
  list<Edge>  intersectQuery(1:Edge edge);
  list<Edge>  notQuery(1:Edge edge);
  list<Edge>  unionQuery(1:Edge edge);
}