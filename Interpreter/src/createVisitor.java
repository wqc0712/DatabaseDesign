// Generated from /Users/tom/IdeaProjects/DatabaseDesign/create.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link createParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface createVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link createParser#exprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprs(createParser.ExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#deleteExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteExpr(createParser.DeleteExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#whereExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereExpr(createParser.WhereExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#condExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondExpr(createParser.CondExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#selectExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectExpr(createParser.SelectExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#insertExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertExpr(createParser.InsertExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#insertAgm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertAgm(createParser.InsertAgmContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#dropExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropExpr(createParser.DropExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#dropIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropIndex(createParser.DropIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#dropTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropTable(createParser.DropTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#createExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateExpr(createParser.CreateExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#agmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgmts(createParser.AgmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#indexExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExpr(createParser.IndexExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#indexAgmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAgmt(createParser.IndexAgmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#tableExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableExpr(createParser.TableExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#tableAgmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAgmt(createParser.TableAgmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#tableAgm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAgm(createParser.TableAgmContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#tableEle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableEle(createParser.TableEleContext ctx);
	/**
	 * Visit a parse tree produced by {@link createParser#tablePrim}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablePrim(createParser.TablePrimContext ctx);
}