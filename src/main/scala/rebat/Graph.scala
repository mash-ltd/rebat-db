package com.mashltd.rebatdb

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

object Graph extends Schema {
  val edges = table[Edge]("Edges")

  on(edges) (ed => declare(
    columns(ed.from_entity_id, ed.from_entity_type, ed.relation_id) are(indexed),
    columns(ed.to_entity_id, ed.to_entity_type, ed.relation_id) are(indexed)
  ))
}