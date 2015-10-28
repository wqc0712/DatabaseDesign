import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

import java.lang.Double;
import java.lang.Override;

public class Visitor extends createBaseVisitor<Double> {
    @Override
    public Double visitExprs(createParser.ExprsContext ctx){
        return visit(ctx.getChild(0));
    }

    @Override
    public Double visitDeleteExpr(createParser.DeleteExprContext ctx){
        Context.getInstance().setMethod("delete");
        Context.getInstance().setTableID(ctx.Tid.getText());
        Context.getInstance().setIndex(false);
        if (ctx.whereExpr() != null)
            visit(ctx.whereExpr());
        return null;
    }

    @Override
    public Double visitWhereExpr(createParser.WhereExprContext ctx){
        visit(ctx.condExpr());
        return null;
    }

    @Override
    public Double visitCondExpr(createParser.CondExprContext ctx){
        String A = new String(ctx.col.getText());
        String B = new String(ctx.op.getText());
        String C = new String(ctx.value.getText());
        int D = -1;
        int E = -1;
        switch (ctx.value.getType()){
            case createParser.INTEGER: {
                D = 0;
                E = 0;
                break;
            }
            case createParser.FLOAT :{
                D = 1;
                E = 0;
                break;
            }
            case createParser.STRING :{
                D = 2;
                C = C.replace("\'","");
                E = 0;
                break;
            }
        }
        Context.getInstance().addCond(A, B, C, D, E);
        if (ctx.condExpr() != null)
            visit(ctx.condExpr());
        return null;
    }

    @Override
    public Double visitSelectExpr(createParser.SelectExprContext ctx){
        Context.getInstance().setMethod("select");
        Context.getInstance().setTableID(ctx.Tid.getText());
        Context.getInstance().setIndex(false);
        if (ctx.whereExpr() != null)
            visit(ctx.whereExpr());
        return null;
    }
    
    @Override
    public Double visitInsertExpr(createParser.InsertExprContext ctx) {
        Context.getInstance().setMethod("insert");
        Context.getInstance().setTableID(ctx.Tid.getText());
        Context.getInstance().setIndex(false);
        String C = new String(ctx.value.getText());
        int D = -1;
        int E = -1;
        switch (ctx.value.getType()){
            case createParser.INTEGER: {
                D = 0;
                E = 0;
                break;
            }
            case createParser.FLOAT :{
                D = 1;
                E = 0;
                break;
            }
            case createParser.STRING :{
                D = 2;
                C = C.replace("\'","");
                E = 0;
                break;
            }
        }
        Context.getInstance().addValue(C,D,E,false);
        if (ctx.insertAgm() != null)
            visit(ctx.insertAgm());
        return null;
    }
    
    @Override
    public Double visitInsertAgm(createParser.InsertAgmContext ctx) {
        String C = new String(ctx.value.getText());
        int D = -1;
        int E = -1;
        switch (ctx.value.getType()){
            case createParser.INTEGER: {
                D = 0;
                E = 0;
                break;
            }
            case createParser.FLOAT :{
                D = 1;
                E = 0;
                break;
            }
            case createParser.STRING :{
                D = 2;
                C = C.replace("\'","");
                E = 0;
                break;
            }
        }
        Context.getInstance().addValue(C,D,E,false);
        if (ctx.insertAgm() != null)
            visit(ctx.insertAgm());
        return null;
    }
    
    @Override
    public Double visitDropExpr(createParser.DropExprContext ctx){
        Context.getInstance().setMethod("drop");
        if (ctx.dropIndex() != null) {
            visit(ctx.dropIndex());
            return null;
        }
        if (ctx.dropTable() != null) {
            visit(ctx.dropTable());
            return null;
        }
        return null;
    }
        
    @Override
    public Double visitDropIndex(createParser.DropIndexContext ctx) {
        Context.getInstance().setIndexID(ctx.indexid.getText());
        Context.getInstance().setIndex(true);
        return null;
    }
    
    @Override
    public Double visitDropTable(createParser.DropTableContext ctx) {
        Context.getInstance().setTableID(ctx.tableid.getText());
        Context.getInstance().setIndex(false);
        return null;
    }
    
    @Override
    public Double visitCreateExpr(createParser.CreateExprContext ctx) {
        Context.getInstance().setMethod("create");
        visit(ctx.agmts());
        return null;
    }
    
    @Override
    public Double visitAgmts(createParser.AgmtsContext ctx){
        if (ctx.tableExpr() != null) {
            visit(ctx.tableExpr());
            return null;
        }
        if (ctx.indexExpr() != null) {
            visit(ctx.indexExpr());
            return null;
        }
        return null;
    }
    
    @Override
    public Double visitIndexExpr(createParser.IndexExprContext ctx) {
        visit(ctx.indexAgmt());
        Context.getInstance().setIndex(true);
        return null;
    }

    @Override
    public Double visitIndexAgmt(createParser.IndexAgmtContext ctx){
        Context.getInstance().setTableID(ctx.tableid.getText());
        Context.getInstance().setIndexID(ctx.indexid.getText());
        Context.getInstance().setListID(ctx.listid.getText());
        return null;
    }

    @Override
    public Double visitTableExpr(createParser.TableExprContext ctx){
        Context.getInstance().setIndex(false);
        visit(ctx.tableAgmt());
        return null;
    }

    @Override
    public Double visitTableAgmt(createParser.TableAgmtContext ctx){
        Context.getInstance().setTableID(ctx.Tid.getText());
        visit(ctx.tableAgm());
        visit(ctx.tablePrim());
        return null;
    }

    @Override
    public Double visitTableAgm(createParser.TableAgmContext ctx){
        visit(ctx.tableEle());
        if (ctx.tableAgm() != null){
            visit(ctx.tableAgm());
        }
        return null;
    }

    @Override
    public Double visitTableEle(createParser.TableEleContext ctx){
        String A = new String(ctx.Aid.getText());
        int B = -1;
        int C = -1;
        Boolean D;
        switch (ctx.type.getType()){
            case createParser.T__18 :{
                B = 0;
                C = 0;
                break;
            }
            case createParser.T__19 :{
                B = 2;
                C = 0;
                break;
            }
            case createParser.CHAR :{
                B = 1;
                String str = ctx.CHAR().getText();
                str = str.replaceAll("\\D","");
                C = Integer.parseInt(str);
                break;
            }
        }
        if (ctx.getText().contains("unique")) {
            D = true;
        } else {
            D = false;
        }
        Context.getInstance().addValue(A,B,C,D);

        return null;
    }

    @Override
    public Double visitTablePrim(createParser.TablePrimContext ctx){
        Context.getInstance().setPK(ctx.Pid.getText());
        return null;
    }
    
    public void print(){
        System.out.println(Context.getInstance().getMethod());
        System.out.println(Context.getInstance().getTableID());
        System.out.println(Context.getInstance().getIndexID());
        System.out.println(Context.getInstance().getListID());
        System.out.println(Context.getInstance().getPK());
        CondExpr temp;
        temp = Context.getInstance().getCond();
        while (temp != null) {
            System.out.println(temp.getID()+" "+temp.getOP());
            Value Vtemp = temp.getValue();
            System.out.println(Vtemp.getData()+" "+Vtemp.getType());
            temp = Context.getInstance().getCond();
        }
        Value T;
        T = Context.getInstance().getValue();
        while (T != null) {
            System.out.println(T.getData()+ " " + T.getType() + " " + T.getLength() + " " + T.IsUnique());
            T = Context.getInstance().getValue();
        }
    }

}
