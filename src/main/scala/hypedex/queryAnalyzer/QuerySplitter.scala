package hypedex.queryAnalyzer

import hypedex.antlr.PredicateParser.NumberContext
import hypedex.antlr.{PredicateBaseListener, PredicateBaseVisitor, PredicateParser}
import hypedex.logicalEngine.model.{Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression}
import hypedex.queryAnalyzer.QuerySplitter.Mapping

/**
  * Split a WHERE clause in to multiple where clauses: one for each variable
  * @param ids
  */
class QuerySplitter(ids: Set[String]) extends PredicateBaseVisitor[Mapping] {
  override def visitEqualCondition (ctx: PredicateParser.EqualConditionContext): Mapping =
    Map(ctx.id().getText -> Set[LogicalExpression](Equals(parseNumber(ctx.number()))))


  override def visitGreaterThanCondition (ctx: PredicateParser.GreaterThanConditionContext): Mapping =
    Map(ctx.id().getText -> Set[LogicalExpression](GreaterThan(parseNumber(ctx.number()))))


  override def visitGreaterThanEqualCondition (ctx: PredicateParser.GreaterThanEqualConditionContext): Mapping =
    Map(ctx.id().getText -> Set[LogicalExpression](GreaterThanEqual(parseNumber(ctx.number()))))


  override def visitLessThanCondition (ctx: PredicateParser.LessThanConditionContext): Mapping =
    Map(ctx.id().getText -> Set[LogicalExpression](LessThan(parseNumber(ctx.number()))))

  override def visitLessThanEqualCondition (ctx: PredicateParser.LessThanEqualConditionContext): Mapping =
    Map(ctx.id().getText -> Set[LogicalExpression](LessThanEqual(parseNumber(ctx.number()))))

  override def visitAndConnection(ctx: PredicateParser.AndConnectionContext): Mapping = {
    mergeMaps(visit(ctx.formula(0)), visit(ctx.formula(1)))
  }

  override def visitOrConnection(ctx: PredicateParser.OrConnectionContext): Mapping = {
    mergeMaps(visit(ctx.formula(0)), visit(ctx.formula(1)))
  }

  private def mergeMaps(x: Mapping, y: Mapping): Mapping = {
    val merged = x.toSeq ++ y.toSeq

    merged.groupBy(_._1).map(pair => (pair._1, pair._2.flatMap(_._2).toSet))
  }

  private def parseNumber(number: NumberContext): Double = number.NUMBER().getText.toDouble
}

object QuerySplitter {
  type Mapping = Map[String, Set[LogicalExpression]]
}
