namespace java com.mashltd.rebatdb.thrift
namespace rb rebat.thrift

struct Edge {
  1: optional i64     fromEntityId; 
  2: optional string  fromEntityType; 
  3: optional i64     toEntityId; 
  4: optional string  toEntityType; 
  5: optional i64     weight = 0;
  6: optional i64     relationId;
}

enum QueryType {
  WHERE,
  INTERSECT,
  UNION,
  NOT
}

struct Query {
  1: required Edge      edge;
  2: optional QueryType qtype = QueryType.WHERE;
}

service RebatDB {
  bool        addQuery(1:Edge edge);
  bool        deleteQuery(1:Edge edge);
  bool        updateWeightQuery(1:Edge edge, 2:i64 weight);
  list<Edge>  selectQuery(1:list<Query> queryList);
}