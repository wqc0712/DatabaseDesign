// Generated from /Users/tom/IdeaProjects/DatabaseDesign/create.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link createParser}.
 */
public interface createListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link createParser#exprs}.
	 * @param ctx the parse tree
	 */
	void enterExprs(createParser.ExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#exprs}.
	 * @param ctx the parse tree
	 */
	void exitExprs(createParser.ExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#deleteExpr}.
	 * @param ctx the parse tree
	 */
	void enterDeleteExpr(createParser.DeleteExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#deleteExpr}.
	 * @param ctx the parse tree
	 */
	void exitDeleteExpr(createParser.DeleteExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#whereExpr}.
	 * @param ctx the parse tree
	 */
	void enterWhereExpr(createParser.WhereExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#whereExpr}.
	 * @param ctx the parse tree
	 */
	void exitWhereExpr(createParser.WhereExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#condExpr}.
	 * @param ctx the parse tree
	 */
	void enterCondExpr(createParser.CondExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#condExpr}.
	 * @param ctx the parse tree
	 */
	void exitCondExpr(createParser.CondExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void enterSelectExpr(createParser.SelectExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#selectExpr}.
	 * @param ctx the parse tree
	 */
	void exitSelectExpr(createParser.SelectExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#insertExpr}.
	 * @param ctx the parse tree
	 */
	void enterInsertExpr(createParser.InsertExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#insertExpr}.
	 * @param ctx the parse tree
	 */
	void exitInsertExpr(createParser.InsertExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#insertAgm}.
	 * @param ctx the parse tree
	 */
	void enterInsertAgm(createParser.InsertAgmContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#insertAgm}.
	 * @param ctx the parse tree
	 */
	void exitInsertAgm(createParser.InsertAgmContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#dropExpr}.
	 * @param ctx the parse tree
	 */
	void enterDropExpr(createParser.DropExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#dropExpr}.
	 * @param ctx the parse tree
	 */
	void exitDropExpr(createParser.DropExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#dropIndex}.
	 * @param ctx the parse tree
	 */
	void enterDropIndex(createParser.DropIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#dropIndex}.
	 * @param ctx the parse tree
	 */
	void exitDropIndex(createParser.DropIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(createParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(createParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#createExpr}.
	 * @param ctx the parse tree
	 */
	void enterCreateExpr(createParser.CreateExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#createExpr}.
	 * @param ctx the parse tree
	 */
	void exitCreateExpr(createParser.CreateExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#agmts}.
	 * @param ctx the parse tree
	 */
	void enterAgmts(createParser.AgmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#agmts}.
	 * @param ctx the parse tree
	 */
	void exitAgmts(createParser.AgmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#indexExpr}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(createParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#indexExpr}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(createParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#indexAgmt}.
	 * @param ctx the parse tree
	 */
	void enterIndexAgmt(createParser.IndexAgmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#indexAgmt}.
	 * @param ctx the parse tree
	 */
	void exitIndexAgmt(createParser.IndexAgmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#tableExpr}.
	 * @param ctx the parse tree
	 */
	void enterTableExpr(createParser.TableExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#tableExpr}.
	 * @param ctx the parse tree
	 */
	void exitTableExpr(createParser.TableExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#tableAgmt}.
	 * @param ctx the parse tree
	 */
	void enterTableAgmt(createParser.TableAgmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#tableAgmt}.
	 * @param ctx the parse tree
	 */
	void exitTableAgmt(createParser.TableAgmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#tableAgm}.
	 * @param ctx the parse tree
	 */
	void enterTableAgm(createParser.TableAgmContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#tableAgm}.
	 * @param ctx the parse tree
	 */
	void exitTableAgm(createParser.TableAgmContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#tableEle}.
	 * @param ctx the parse tree
	 */
	void enterTableEle(createParser.TableEleContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#tableEle}.
	 * @param ctx the parse tree
	 */
	void exitTableEle(createParser.TableEleContext ctx);
	/**
	 * Enter a parse tree produced by {@link createParser#tablePrim}.
	 * @param ctx the parse tree
	 */
	void enterTablePrim(createParser.TablePrimContext ctx);
	/**
	 * Exit a parse tree produced by {@link createParser#tablePrim}.
	 * @param ctx the parse tree
	 */
	void exitTablePrim(createParser.TablePrimContext ctx);
}