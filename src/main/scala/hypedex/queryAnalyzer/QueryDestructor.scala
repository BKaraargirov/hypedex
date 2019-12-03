package hypedex.queryAnalyzer

import hypedex.antlr.PredicateParser.NumberContext
import hypedex.antlr.{PredicateBaseListener, PredicateBaseVisitor, PredicateParser}
import hypedex.models.DimensionPredicate
import hypedex.queryAnalyzer.QueryDestructor.Mapping
import hypedex.queryAnalyzer.models.{AndNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression, LogicalTreeNode}

import scala.collection.mutable


/**
  * Split a WHERE clause in to multiple where clauses: one for each variable
  * @param ids
  */
class QueryDestructor(ids: Set[String]) extends PredicateBaseListener {
  private var predicates: mutable.Map[String, List[LogicalTreeNode]] = ids.map(_ -> List()) // x :: list list.tail(pop) / head

  override def exitAndConnection(ctx: PredicateParser.AndConnectionContext): Unit = {
    ids.foreach(id => {
      if(predicates(id).nonEmpty) {
        predicates(id) = List(AndNode(predicates(id)))
      }
    })
  }

  override def enterEqualCondition(ctx: PredicateParser.EqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = Equals(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def enterGreaterThanCondition (ctx: PredicateParser.GreaterThanConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = GreaterThan(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def enterGreaterThanEqualCondition (ctx: PredicateParser.GreaterThanEqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = GreaterThanEqual(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def enterLessThanCondition (ctx: PredicateParser.LessThanConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = LessThan(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def enterLessThanEqualCondition (ctx: PredicateParser.LessThanEqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = LessThanEqual(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

//  override def visitAndConnection(ctx: PredicateParser.AndConnectionContext): Mapping = {
//    mergeMaps(visit(ctx.formula(0)), visit(ctx.formula(1)))
//  }
//
//  override def visitOrConnection(ctx: PredicateParser.OrConnectionContext): Mapping = {
//    mergeMaps(visit(ctx.formula(0)), visit(ctx.formula(1)))
//  }

   private def parseNumber(number: NumberContext): Double = number.NUMBER().getText.toDouble
}

object QueryDestructor {
  type Mapping = Map[String, DimensionPredicate]
}
