package hypedex.antlr;

// Generated from Predicate.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PredicateLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, OR=8, AND=9, NOT=10, 
		ID=11, NUMBER=12, NEWLINE=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "OR", "AND", 
			"NOT", "ID", "NUMBER", "NEWLINE", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'='", "'<'", "'>'", "'<='", "'>='", "'OR'", "'AND'", 
			"'!'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "OR", "AND", "NOT", "ID", 
			"NUMBER", "NEWLINE", "WS"
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


	public PredicateLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Predicate.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20]\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3"+
		"\13\3\f\3\f\7\f;\n\f\f\f\16\f>\13\f\3\r\5\rA\n\r\3\r\6\rD\n\r\r\r\16\r"+
		"E\3\r\3\r\6\rJ\n\r\r\r\16\rK\5\rN\n\r\3\16\5\16Q\n\16\3\16\3\16\3\16\3"+
		"\16\3\17\6\17X\n\17\r\17\16\17Y\3\17\3\17\3<\2\20\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\5\4\2C\\c|\5\2\62"+
		";C\\c|\3\2\13\13\2c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2"+
		"\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\'\3\2\2\2\r)\3\2\2\2\17,\3\2\2"+
		"\2\21/\3\2\2\2\23\62\3\2\2\2\25\66\3\2\2\2\278\3\2\2\2\31@\3\2\2\2\33"+
		"P\3\2\2\2\35W\3\2\2\2\37 \7*\2\2 \4\3\2\2\2!\"\7+\2\2\"\6\3\2\2\2#$\7"+
		"?\2\2$\b\3\2\2\2%&\7>\2\2&\n\3\2\2\2\'(\7@\2\2(\f\3\2\2\2)*\7>\2\2*+\7"+
		"?\2\2+\16\3\2\2\2,-\7@\2\2-.\7?\2\2.\20\3\2\2\2/\60\7Q\2\2\60\61\7T\2"+
		"\2\61\22\3\2\2\2\62\63\7C\2\2\63\64\7P\2\2\64\65\7F\2\2\65\24\3\2\2\2"+
		"\66\67\7#\2\2\67\26\3\2\2\28<\t\2\2\29;\t\3\2\2:9\3\2\2\2;>\3\2\2\2<="+
		"\3\2\2\2<:\3\2\2\2=\30\3\2\2\2><\3\2\2\2?A\7/\2\2@?\3\2\2\2@A\3\2\2\2"+
		"AC\3\2\2\2BD\4\62;\2CB\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2\2\2FM\3\2\2\2"+
		"GI\7\60\2\2HJ\4\62;\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2"+
		"\2MG\3\2\2\2MN\3\2\2\2N\32\3\2\2\2OQ\7\17\2\2PO\3\2\2\2PQ\3\2\2\2QR\3"+
		"\2\2\2RS\7\f\2\2ST\3\2\2\2TU\b\16\2\2U\34\3\2\2\2VX\t\4\2\2WV\3\2\2\2"+
		"XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\b\17\2\2\\\36\3\2\2\2\n\2"+
		"<@EKMPY\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}