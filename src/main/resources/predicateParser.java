// Generated from predicate.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class predicateParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, EQUAL=3, LESSTHAN=4, MORETHAN=5, LESSTHANEQ=6, MORETHANEQ=7, 
		NOT=8, AND=9, OR=10, CHARACTER=11, ENDLINE=12, WHITESPACE=13;
	public static final int
		RULE_formula = 0, RULE_term = 1, RULE_bin_connective = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"formula", "term", "bin_connective"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'='", "'<'", "'>'", "'<='", "'>='", "'!'", "'AND'", 
			"'OR'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LPAREN", "RPAREN", "EQUAL", "LESSTHAN", "MORETHAN", "LESSTHANEQ", 
			"MORETHANEQ", "NOT", "AND", "OR", "CHARACTER", "ENDLINE", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "predicate.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public predicateParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FormulaContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(predicateParser.NOT, 0); }
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(predicateParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(predicateParser.RPAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(predicateParser.EQUAL, 0); }
		public TerminalNode LESSTHAN() { return getToken(predicateParser.LESSTHAN, 0); }
		public TerminalNode MORETHAN() { return getToken(predicateParser.MORETHAN, 0); }
		public TerminalNode LESSTHANEQ() { return getToken(predicateParser.LESSTHANEQ, 0); }
		public TerminalNode MORETHANEQ() { return getToken(predicateParser.MORETHANEQ, 0); }
		public Bin_connectiveContext bin_connective() {
			return getRuleContext(Bin_connectiveContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		return formula(0);
	}

	private FormulaContext formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FormulaContext _localctx = new FormulaContext(_ctx, _parentState);
		FormulaContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_formula, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(7);
				match(NOT);
				setState(8);
				formula(8);
				}
				break;
			case 2:
				{
				setState(9);
				match(LPAREN);
				setState(10);
				formula(0);
				setState(11);
				match(RPAREN);
				setState(12);
				formula(7);
				}
				break;
			case 3:
				{
				setState(14);
				match(LPAREN);
				setState(15);
				formula(0);
				setState(16);
				match(RPAREN);
				setState(17);
				formula(6);
				}
				break;
			case 4:
				{
				setState(19);
				term();
				setState(20);
				match(EQUAL);
				setState(21);
				term();
				}
				break;
			case 5:
				{
				setState(23);
				term();
				setState(24);
				match(LESSTHAN);
				setState(25);
				term();
				}
				break;
			case 6:
				{
				setState(27);
				term();
				setState(28);
				match(MORETHAN);
				setState(29);
				term();
				}
				break;
			case 7:
				{
				setState(31);
				term();
				setState(32);
				match(LESSTHANEQ);
				setState(33);
				term();
				}
				break;
			case 8:
				{
				setState(35);
				term();
				setState(36);
				match(MORETHANEQ);
				setState(37);
				term();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(47);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new FormulaContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_formula);
					setState(41);
					if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
					setState(42);
					bin_connective();
					setState(43);
					formula(10);
					}
					} 
				}
				setState(49);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public List<TerminalNode> CHARACTER() { return getTokens(predicateParser.CHARACTER); }
		public TerminalNode CHARACTER(int i) {
			return getToken(predicateParser.CHARACTER, i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_term);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(51); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(50);
					match(CHARACTER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(53); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bin_connectiveContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(predicateParser.AND, 0); }
		public TerminalNode OR() { return getToken(predicateParser.OR, 0); }
		public Bin_connectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bin_connective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).enterBin_connective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof predicateListener ) ((predicateListener)listener).exitBin_connective(this);
		}
	}

	public final Bin_connectiveContext bin_connective() throws RecognitionException {
		Bin_connectiveContext _localctx = new Bin_connectiveContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_bin_connective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return formula_sempred((FormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean formula_sempred(FormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17<\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\5\2*\n\2\3\2\3\2\3\2\3\2\7\2\60\n\2\f\2\16\2\63\13\2\3\3\6\3\66\n\3"+
		"\r\3\16\3\67\3\4\3\4\3\4\2\3\2\5\2\4\6\2\3\3\2\13\f\2A\2)\3\2\2\2\4\65"+
		"\3\2\2\2\69\3\2\2\2\b\t\b\2\1\2\t\n\7\n\2\2\n*\5\2\2\n\13\f\7\3\2\2\f"+
		"\r\5\2\2\2\r\16\7\4\2\2\16\17\5\2\2\t\17*\3\2\2\2\20\21\7\3\2\2\21\22"+
		"\5\2\2\2\22\23\7\4\2\2\23\24\5\2\2\b\24*\3\2\2\2\25\26\5\4\3\2\26\27\7"+
		"\5\2\2\27\30\5\4\3\2\30*\3\2\2\2\31\32\5\4\3\2\32\33\7\6\2\2\33\34\5\4"+
		"\3\2\34*\3\2\2\2\35\36\5\4\3\2\36\37\7\7\2\2\37 \5\4\3\2 *\3\2\2\2!\""+
		"\5\4\3\2\"#\7\b\2\2#$\5\4\3\2$*\3\2\2\2%&\5\4\3\2&\'\7\t\2\2\'(\5\4\3"+
		"\2(*\3\2\2\2)\b\3\2\2\2)\13\3\2\2\2)\20\3\2\2\2)\25\3\2\2\2)\31\3\2\2"+
		"\2)\35\3\2\2\2)!\3\2\2\2)%\3\2\2\2*\61\3\2\2\2+,\f\13\2\2,-\5\6\4\2-."+
		"\5\2\2\f.\60\3\2\2\2/+\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2"+
		"\62\3\3\2\2\2\63\61\3\2\2\2\64\66\7\r\2\2\65\64\3\2\2\2\66\67\3\2\2\2"+
		"\67\65\3\2\2\2\678\3\2\2\28\5\3\2\2\29:\t\2\2\2:\7\3\2\2\2\5)\61\67";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}