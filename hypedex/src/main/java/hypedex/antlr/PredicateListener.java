// Generated from Predicate.g4 by ANTLR 4.7.2

package hypedex.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PredicateParser}.
 */
public interface PredicateListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PredicateParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(PredicateParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link PredicateParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(PredicateParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterAndConnection(PredicateParser.AndConnectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitAndConnection(PredicateParser.AndConnectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterNegation(PredicateParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitNegation(PredicateParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Paranthesis}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterParanthesis(PredicateParser.ParanthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paranthesis}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitParanthesis(PredicateParser.ParanthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionNode}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterConditionNode(PredicateParser.ConditionNodeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionNode}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitConditionNode(PredicateParser.ConditionNodeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterOrConnection(PredicateParser.OrConnectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrConnection}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitOrConnection(PredicateParser.OrConnectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterEqualCondition(PredicateParser.EqualConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitEqualCondition(PredicateParser.EqualConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterLessThanCondition(PredicateParser.LessThanConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitLessThanCondition(PredicateParser.LessThanConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanCondition(PredicateParser.GreaterThanConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanCondition(PredicateParser.GreaterThanConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterLessThanEqualCondition(PredicateParser.LessThanEqualConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitLessThanEqualCondition(PredicateParser.LessThanEqualConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanEqualCondition(PredicateParser.GreaterThanEqualConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanEqualCondition}
	 * labeled alternative in {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanEqualCondition(PredicateParser.GreaterThanEqualConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PredicateParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(PredicateParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link PredicateParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(PredicateParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link PredicateParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(PredicateParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PredicateParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(PredicateParser.NumberContext ctx);
}