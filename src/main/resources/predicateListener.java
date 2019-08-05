// Generated from predicate.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link predicateParser}.
 */
public interface predicateListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link predicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(predicateParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link predicateParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(predicateParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link predicateParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(predicateParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link predicateParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(predicateParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link predicateParser#bin_connective}.
	 * @param ctx the parse tree
	 */
	void enterBin_connective(predicateParser.Bin_connectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link predicateParser#bin_connective}.
	 * @param ctx the parse tree
	 */
	void exitBin_connective(predicateParser.Bin_connectiveContext ctx);
}