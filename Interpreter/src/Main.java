import java.util.Scanner;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
/**
 * Created by tom on 15/10/26.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            while (line.contains(";") == false) {
                line = line + scanner.nextLine();
            }
            if (line.equals("quit;")) return;
            if (line != null) {

                line = line.trim();
                Context.getInstance().clean();
                ANTLRInputStream input = new ANTLRInputStream(line);
                createLexer lexer = new createLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                createParser parser = new createParser(tokens);
                parser.removeErrorListeners();
                parser.addErrorListener(new SyntaxErrorListener());
                ParseTree tree = parser.exprs();
                Visitor visitor = new Visitor();
                try {
                    parser.setErrorHandler(new ErrorStrategy());
                    visitor.visit(tree);
                    if (Context.getInstance().getError()) throw new Exception();
                } catch (Exception e) {
                    continue;
                }
                visitor.print();
            }
        }
    }
}
