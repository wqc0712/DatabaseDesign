// Generated from /Users/tom/IdeaProjects/DatabaseDesign/create.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class createParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, CHAR=24, STRING=25, 
		ID=26, OP=27, INTEGER=28, INT=29, FLOAT=30, WS=31;
	public static final int
		RULE_exprs = 0, RULE_deleteExpr = 1, RULE_whereExpr = 2, RULE_condExpr = 3, 
		RULE_selectExpr = 4, RULE_insertExpr = 5, RULE_insertAgm = 6, RULE_dropExpr = 7, 
		RULE_dropIndex = 8, RULE_dropTable = 9, RULE_createExpr = 10, RULE_agmts = 11, 
		RULE_indexExpr = 12, RULE_indexAgmt = 13, RULE_tableExpr = 14, RULE_tableAgmt = 15, 
		RULE_tableAgm = 16, RULE_tableEle = 17, RULE_tablePrim = 18;
	public static final String[] ruleNames = {
		"exprs", "deleteExpr", "whereExpr", "condExpr", "selectExpr", "insertExpr", 
		"insertAgm", "dropExpr", "dropIndex", "dropTable", "createExpr", "agmts", 
		"indexExpr", "indexAgmt", "tableExpr", "tableAgmt", "tableAgm", "tableEle", 
		"tablePrim"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'delete'", "'from'", "'where'", "'and'", "'select'", "'*'", 
		"'insert'", "'into'", "'values'", "'('", "')'", "','", "'drop'", "'index'", 
		"'table'", "'create'", "'on'", "'int'", "'float'", "'unique'", "'primary'", 
		"'key'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"CHAR", "STRING", "ID", "OP", "INTEGER", "INT", "FLOAT", "WS"
	};
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
	public String getGrammarFileName() { return "create.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public createParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprsContext extends ParserRuleContext {
		public CreateExprContext createExpr() {
			return getRuleContext(CreateExprContext.class,0);
		}
		public DropExprContext dropExpr() {
			return getRuleContext(DropExprContext.class,0);
		}
		public InsertExprContext insertExpr() {
			return getRuleContext(InsertExprContext.class,0);
		}
		public DeleteExprContext deleteExpr() {
			return getRuleContext(DeleteExprContext.class,0);
		}
		public SelectExprContext selectExpr() {
			return getRuleContext(SelectExprContext.class,0);
		}
		public ExprsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterExprs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitExprs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitExprs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprsContext exprs() throws RecognitionException {
		ExprsContext _localctx = new ExprsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_exprs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			switch (_input.LA(1)) {
			case T__16:
				{
				setState(38);
				createExpr();
				}
				break;
			case T__13:
				{
				setState(39);
				dropExpr();
				}
				break;
			case T__7:
				{
				setState(40);
				insertExpr();
				}
				break;
			case T__1:
				{
				setState(41);
				deleteExpr();
				}
				break;
			case T__5:
				{
				setState(42);
				selectExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(45);
			match(T__0);
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

	public static class DeleteExprContext extends ParserRuleContext {
		public Token Tid;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public WhereExprContext whereExpr() {
			return getRuleContext(WhereExprContext.class,0);
		}
		public DeleteExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterDeleteExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitDeleteExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitDeleteExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteExprContext deleteExpr() throws RecognitionException {
		DeleteExprContext _localctx = new DeleteExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_deleteExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__1);
			setState(48);
			match(T__2);
			setState(49);
			((DeleteExprContext)_localctx).Tid = match(ID);
			setState(51);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(50);
				whereExpr();
				}
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

	public static class WhereExprContext extends ParserRuleContext {
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public WhereExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterWhereExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitWhereExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitWhereExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereExprContext whereExpr() throws RecognitionException {
		WhereExprContext _localctx = new WhereExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_whereExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(T__3);
			setState(54);
			condExpr();
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

	public static class CondExprContext extends ParserRuleContext {
		public Token col;
		public Token op;
		public Token value;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public TerminalNode OP() { return getToken(createParser.OP, 0); }
		public TerminalNode INTEGER() { return getToken(createParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(createParser.STRING, 0); }
		public TerminalNode FLOAT() { return getToken(createParser.FLOAT, 0); }
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public CondExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterCondExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitCondExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitCondExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondExprContext condExpr() throws RecognitionException {
		CondExprContext _localctx = new CondExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_condExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			((CondExprContext)_localctx).col = match(ID);
			setState(57);
			((CondExprContext)_localctx).op = match(OP);
			setState(58);
			((CondExprContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT))) != 0)) ) {
				((CondExprContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(61);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(59);
				match(T__4);
				setState(60);
				condExpr();
				}
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

	public static class SelectExprContext extends ParserRuleContext {
		public Token Tid;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public WhereExprContext whereExpr() {
			return getRuleContext(WhereExprContext.class,0);
		}
		public SelectExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterSelectExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitSelectExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitSelectExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectExprContext selectExpr() throws RecognitionException {
		SelectExprContext _localctx = new SelectExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_selectExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__5);
			setState(64);
			match(T__6);
			setState(65);
			match(T__2);
			setState(66);
			((SelectExprContext)_localctx).Tid = match(ID);
			setState(68);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(67);
				whereExpr();
				}
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

	public static class InsertExprContext extends ParserRuleContext {
		public Token Tid;
		public Token value;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public TerminalNode INTEGER() { return getToken(createParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(createParser.STRING, 0); }
		public TerminalNode FLOAT() { return getToken(createParser.FLOAT, 0); }
		public InsertAgmContext insertAgm() {
			return getRuleContext(InsertAgmContext.class,0);
		}
		public InsertExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterInsertExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitInsertExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitInsertExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertExprContext insertExpr() throws RecognitionException {
		InsertExprContext _localctx = new InsertExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_insertExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__7);
			setState(71);
			match(T__8);
			setState(72);
			((InsertExprContext)_localctx).Tid = match(ID);
			setState(73);
			match(T__9);
			setState(74);
			match(T__10);
			setState(75);
			((InsertExprContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT))) != 0)) ) {
				((InsertExprContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(77);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(76);
				insertAgm();
				}
			}

			setState(79);
			match(T__11);
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

	public static class InsertAgmContext extends ParserRuleContext {
		public Token value;
		public TerminalNode INTEGER() { return getToken(createParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(createParser.STRING, 0); }
		public TerminalNode FLOAT() { return getToken(createParser.FLOAT, 0); }
		public InsertAgmContext insertAgm() {
			return getRuleContext(InsertAgmContext.class,0);
		}
		public InsertAgmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertAgm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterInsertAgm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitInsertAgm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitInsertAgm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertAgmContext insertAgm() throws RecognitionException {
		InsertAgmContext _localctx = new InsertAgmContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_insertAgm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__12);
			setState(82);
			((InsertAgmContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT))) != 0)) ) {
				((InsertAgmContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(84);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(83);
				insertAgm();
				}
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

	public static class DropExprContext extends ParserRuleContext {
		public DropIndexContext dropIndex() {
			return getRuleContext(DropIndexContext.class,0);
		}
		public DropTableContext dropTable() {
			return getRuleContext(DropTableContext.class,0);
		}
		public DropExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterDropExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitDropExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitDropExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DropExprContext dropExpr() throws RecognitionException {
		DropExprContext _localctx = new DropExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_dropExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(T__13);
			setState(89);
			switch (_input.LA(1)) {
			case T__14:
				{
				setState(87);
				dropIndex();
				}
				break;
			case T__15:
				{
				setState(88);
				dropTable();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class DropIndexContext extends ParserRuleContext {
		public Token indexid;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public DropIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterDropIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitDropIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitDropIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DropIndexContext dropIndex() throws RecognitionException {
		DropIndexContext _localctx = new DropIndexContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_dropIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(T__14);
			setState(92);
			((DropIndexContext)_localctx).indexid = match(ID);
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

	public static class DropTableContext extends ParserRuleContext {
		public Token tableid;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public DropTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterDropTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitDropTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitDropTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DropTableContext dropTable() throws RecognitionException {
		DropTableContext _localctx = new DropTableContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_dropTable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(T__15);
			setState(95);
			((DropTableContext)_localctx).tableid = match(ID);
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

	public static class CreateExprContext extends ParserRuleContext {
		public AgmtsContext agmts() {
			return getRuleContext(AgmtsContext.class,0);
		}
		public CreateExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterCreateExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitCreateExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitCreateExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateExprContext createExpr() throws RecognitionException {
		CreateExprContext _localctx = new CreateExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_createExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__16);
			setState(98);
			agmts();
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

	public static class AgmtsContext extends ParserRuleContext {
		public TableExprContext tableExpr() {
			return getRuleContext(TableExprContext.class,0);
		}
		public IndexExprContext indexExpr() {
			return getRuleContext(IndexExprContext.class,0);
		}
		public AgmtsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agmts; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterAgmts(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitAgmts(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitAgmts(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgmtsContext agmts() throws RecognitionException {
		AgmtsContext _localctx = new AgmtsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_agmts);
		try {
			setState(102);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				tableExpr();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				indexExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class IndexExprContext extends ParserRuleContext {
		public IndexAgmtContext indexAgmt() {
			return getRuleContext(IndexAgmtContext.class,0);
		}
		public IndexExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterIndexExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitIndexExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitIndexExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexExprContext indexExpr() throws RecognitionException {
		IndexExprContext _localctx = new IndexExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_indexExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__14);
			setState(105);
			indexAgmt();
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

	public static class IndexAgmtContext extends ParserRuleContext {
		public Token indexid;
		public Token tableid;
		public Token listid;
		public List<TerminalNode> ID() { return getTokens(createParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(createParser.ID, i);
		}
		public IndexAgmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexAgmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterIndexAgmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitIndexAgmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitIndexAgmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexAgmtContext indexAgmt() throws RecognitionException {
		IndexAgmtContext _localctx = new IndexAgmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_indexAgmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			((IndexAgmtContext)_localctx).indexid = match(ID);
			setState(108);
			match(T__17);
			setState(109);
			((IndexAgmtContext)_localctx).tableid = match(ID);
			setState(110);
			match(T__10);
			setState(111);
			((IndexAgmtContext)_localctx).listid = match(ID);
			setState(112);
			match(T__11);
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

	public static class TableExprContext extends ParserRuleContext {
		public TableAgmtContext tableAgmt() {
			return getRuleContext(TableAgmtContext.class,0);
		}
		public TableExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterTableExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitTableExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitTableExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableExprContext tableExpr() throws RecognitionException {
		TableExprContext _localctx = new TableExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_tableExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(T__15);
			setState(115);
			tableAgmt();
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

	public static class TableAgmtContext extends ParserRuleContext {
		public Token Tid;
		public TableAgmContext tableAgm() {
			return getRuleContext(TableAgmContext.class,0);
		}
		public TablePrimContext tablePrim() {
			return getRuleContext(TablePrimContext.class,0);
		}
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public TableAgmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableAgmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterTableAgmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitTableAgmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitTableAgmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableAgmtContext tableAgmt() throws RecognitionException {
		TableAgmtContext _localctx = new TableAgmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_tableAgmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			((TableAgmtContext)_localctx).Tid = match(ID);
			setState(118);
			match(T__10);
			setState(119);
			tableAgm();
			setState(120);
			tablePrim();
			setState(121);
			match(T__11);
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

	public static class TableAgmContext extends ParserRuleContext {
		public TableEleContext tableEle() {
			return getRuleContext(TableEleContext.class,0);
		}
		public TableAgmContext tableAgm() {
			return getRuleContext(TableAgmContext.class,0);
		}
		public TableAgmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableAgm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterTableAgm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitTableAgm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitTableAgm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableAgmContext tableAgm() throws RecognitionException {
		TableAgmContext _localctx = new TableAgmContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_tableAgm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			tableEle();
			setState(124);
			match(T__12);
			setState(126);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(125);
				tableAgm();
				}
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

	public static class TableEleContext extends ParserRuleContext {
		public Token Aid;
		public Token type;
		public Token uni;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public TerminalNode CHAR() { return getToken(createParser.CHAR, 0); }
		public TableEleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableEle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterTableEle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitTableEle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitTableEle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableEleContext tableEle() throws RecognitionException {
		TableEleContext _localctx = new TableEleContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_tableEle);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			((TableEleContext)_localctx).Aid = match(ID);
			setState(129);
			((TableEleContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << CHAR))) != 0)) ) {
				((TableEleContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(131);
			_la = _input.LA(1);
			if (_la==T__20) {
				{
				setState(130);
				((TableEleContext)_localctx).uni = match(T__20);
				}
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

	public static class TablePrimContext extends ParserRuleContext {
		public Token Pid;
		public TerminalNode ID() { return getToken(createParser.ID, 0); }
		public TablePrimContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablePrim; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).enterTablePrim(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof createListener ) ((createListener)listener).exitTablePrim(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof createVisitor ) return ((createVisitor<? extends T>)visitor).visitTablePrim(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablePrimContext tablePrim() throws RecognitionException {
		TablePrimContext _localctx = new TablePrimContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_tablePrim);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__21);
			setState(134);
			match(T__22);
			setState(135);
			match(T__10);
			setState(136);
			((TablePrimContext)_localctx).Pid = match(ID);
			setState(137);
			match(T__11);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3!\u008e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\5\2.\n\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\5\3\66\n\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\5\5@\n\5\3\6\3\6\3\6\3"+
		"\6\3\6\5\6G\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7P\n\7\3\7\3\7\3\b\3\b\3"+
		"\b\5\bW\n\b\3\t\3\t\3\t\5\t\\\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\r\3\r\5\ri\n\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\5\22\u0081"+
		"\n\22\3\23\3\23\3\23\5\23\u0086\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\2\2\25\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&\2\4\5\2\33\33\36\36"+
		"  \4\2\25\26\32\32\u0087\2-\3\2\2\2\4\61\3\2\2\2\6\67\3\2\2\2\b:\3\2\2"+
		"\2\nA\3\2\2\2\fH\3\2\2\2\16S\3\2\2\2\20X\3\2\2\2\22]\3\2\2\2\24`\3\2\2"+
		"\2\26c\3\2\2\2\30h\3\2\2\2\32j\3\2\2\2\34m\3\2\2\2\36t\3\2\2\2 w\3\2\2"+
		"\2\"}\3\2\2\2$\u0082\3\2\2\2&\u0087\3\2\2\2(.\5\26\f\2).\5\20\t\2*.\5"+
		"\f\7\2+.\5\4\3\2,.\5\n\6\2-(\3\2\2\2-)\3\2\2\2-*\3\2\2\2-+\3\2\2\2-,\3"+
		"\2\2\2./\3\2\2\2/\60\7\3\2\2\60\3\3\2\2\2\61\62\7\4\2\2\62\63\7\5\2\2"+
		"\63\65\7\34\2\2\64\66\5\6\4\2\65\64\3\2\2\2\65\66\3\2\2\2\66\5\3\2\2\2"+
		"\678\7\6\2\289\5\b\5\29\7\3\2\2\2:;\7\34\2\2;<\7\35\2\2<?\t\2\2\2=>\7"+
		"\7\2\2>@\5\b\5\2?=\3\2\2\2?@\3\2\2\2@\t\3\2\2\2AB\7\b\2\2BC\7\t\2\2CD"+
		"\7\5\2\2DF\7\34\2\2EG\5\6\4\2FE\3\2\2\2FG\3\2\2\2G\13\3\2\2\2HI\7\n\2"+
		"\2IJ\7\13\2\2JK\7\34\2\2KL\7\f\2\2LM\7\r\2\2MO\t\2\2\2NP\5\16\b\2ON\3"+
		"\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\7\16\2\2R\r\3\2\2\2ST\7\17\2\2TV\t\2\2\2"+
		"UW\5\16\b\2VU\3\2\2\2VW\3\2\2\2W\17\3\2\2\2X[\7\20\2\2Y\\\5\22\n\2Z\\"+
		"\5\24\13\2[Y\3\2\2\2[Z\3\2\2\2\\\21\3\2\2\2]^\7\21\2\2^_\7\34\2\2_\23"+
		"\3\2\2\2`a\7\22\2\2ab\7\34\2\2b\25\3\2\2\2cd\7\23\2\2de\5\30\r\2e\27\3"+
		"\2\2\2fi\5\36\20\2gi\5\32\16\2hf\3\2\2\2hg\3\2\2\2i\31\3\2\2\2jk\7\21"+
		"\2\2kl\5\34\17\2l\33\3\2\2\2mn\7\34\2\2no\7\24\2\2op\7\34\2\2pq\7\r\2"+
		"\2qr\7\34\2\2rs\7\16\2\2s\35\3\2\2\2tu\7\22\2\2uv\5 \21\2v\37\3\2\2\2"+
		"wx\7\34\2\2xy\7\r\2\2yz\5\"\22\2z{\5&\24\2{|\7\16\2\2|!\3\2\2\2}~\5$\23"+
		"\2~\u0080\7\17\2\2\177\u0081\5\"\22\2\u0080\177\3\2\2\2\u0080\u0081\3"+
		"\2\2\2\u0081#\3\2\2\2\u0082\u0083\7\34\2\2\u0083\u0085\t\3\2\2\u0084\u0086"+
		"\7\27\2\2\u0085\u0084\3\2\2\2\u0085\u0086\3\2\2\2\u0086%\3\2\2\2\u0087"+
		"\u0088\7\30\2\2\u0088\u0089\7\31\2\2\u0089\u008a\7\r\2\2\u008a\u008b\7"+
		"\34\2\2\u008b\u008c\7\16\2\2\u008c\'\3\2\2\2\f-\65?FOV[h\u0080\u0085";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}