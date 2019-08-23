package hypedex.antlr;

// Generated from Predicate.g4 by ANTLR 4.7.2
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
	 * Enter a parse tree produced by the {@code SingleFormula}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterSingleFormula(PredicateParser.SingleFormulaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleFormula}
	 * labeled alternative in {@link PredicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitSingleFormula(PredicateParser.SingleFormulaContext ctx);
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
	 * Enter a parse tree produced by {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(PredicateParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PredicateParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(PredicateParser.ConditionContext ctx);
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
}