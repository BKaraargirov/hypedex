package hypedex.queryAnalyzer

import hypedex.antlr.PredicateBaseListener

import scala.collection.mutable

class IdExtractor extends PredicateBaseListener {
  val ids: mutable.Set[String] = mutable.HashSet[String]()

  override def enterId(ctx: _root_.hypedex.antlr.PredicateParser.IdContext): Unit = {
    this.ids.add(ctx.ID().toString)
  }
}
