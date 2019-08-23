package hypedex.queryAnalyzer

import hypedex.antlr.{PredicateBaseListener, PredicateBaseVisitor, PredicateParser}

import scala.collection.mutable

class QuerySplitter(ids: Set[String]) extends PredicateBaseVisitor[Map[String, String]] {
  override def visitCondition(ctx: PredicateParser.ConditionContext): Map[String, String] = {
    Map(ctx.id().getText -> ctx.getText)
  }

  override def visitAndConnection(ctx: PredicateParser.AndConnectionContext): Map[String, String] = {
    visit(ctx.formula(0)) ++ visit(ctx.formula(1))
  }

  override def visitOrConnection(ctx: PredicateParser.OrConnectionContext): Map[String, String] = {
    visit(ctx.formula(0)) ++ visit(ctx.formula(1))
  }
}
