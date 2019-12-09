// Generated from Predicate.g4 by ANTLR 4.7.2

package hypedex.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PredicateParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PredicateVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PredicateParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(PredicateParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndConnection(PredicateParser.AndConnectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(PredicateParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Paranthesis}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParanthesis(PredicateParser.ParanthesisContext ctx);

	/**
	 * Visit a parse tree produced by the {@code OrConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrConnection(PredicateParser.OrConnectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualCondition(PredicateParser.EqualConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanCondition(PredicateParser.LessThanConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanCondition(PredicateParser.GreaterThanConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanEqualCondition(PredicateParser.LessThanEqualConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanEqualCondition(PredicateParser.GreaterThanEqualConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PredicateParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(PredicateParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link PredicateParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(PredicateParser.NumberContext ctx);
}