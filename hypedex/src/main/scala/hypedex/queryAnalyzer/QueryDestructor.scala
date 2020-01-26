package hypedex.queryAnalyzer

import hypedex.antlr.PredicateParser.NumberContext
import hypedex.antlr.{PredicateBaseListener, PredicateBaseVisitor, PredicateParser}
import hypedex.partitionConstructor.DimensionPredicate
import hypedex.queryAnalyzer.QueryDestructor.Mapping
import hypedex.queryAnalyzer.models.{AndNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression, LogicalTreeNode, OrNode}

import scala.collection.mutable


/**
  * Split a WHERE clause in to multiple where clauses: one for each variable
  * @param ids
  */
class QueryDestructor(ids: Set[String]) extends PredicateBaseListener {
  private val predicates: mutable.Map[String, List[LogicalTreeNode]] = mutable.Map(ids.map(_ -> List[LogicalTreeNode]()).toSeq : _*) // x :: list list.tail(pop) / head

  override def exitAndConnection(ctx: PredicateParser.AndConnectionContext): Unit = {
    ids.foreach(id => {
      if(predicates(id).nonEmpty) {
        predicates(id) = List(AndNode(predicates(id)))
      }
    })
  }

  override def exitOrConnection(ctx: PredicateParser.OrConnectionContext): Unit = {
    ids.foreach(id => {
      if(predicates(id).nonEmpty) {
        predicates(id) = List(OrNode(predicates(id)))
      }
    })
  }

  override def exitEqualCondition(ctx: PredicateParser.EqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = Equals(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def exitGreaterThanCondition (ctx: PredicateParser.GreaterThanConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = GreaterThan(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def exitGreaterThanEqualCondition (ctx: PredicateParser.GreaterThanEqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = GreaterThanEqual(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def exitLessThanCondition (ctx: PredicateParser.LessThanConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = LessThan(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  override def exitLessThanEqualCondition (ctx: PredicateParser.LessThanEqualConditionContext): Unit = {
    val id = ctx.id().getText
    val expression = LessThanEqual(parseNumber(ctx.number()))

    predicates(id) = expression:: predicates(id)
  }

  def getPredicates(): Map[String, LogicalTreeNode] =
    this.predicates
        .filter(_._2.nonEmpty)
        .map { case (k, v) => (k, v.head) }
        .toMap

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
  type Mapping = Map[String, LogicalTreeNode]
}
