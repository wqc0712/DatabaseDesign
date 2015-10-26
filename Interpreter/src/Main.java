import java.util.Scanner;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
/**
 * Created by tom on 15/10/26.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (line.contains(";") == false){
            line = line + scanner.nextLine();
        }
        if (line != null) {
            line = line.trim();
            ANTLRInputStream input = new ANTLRInputStream(line);
            createLexer lexer = new createLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            createParser parser = new createParser(tokens);
            ParseTree tree = parser.exprs();
            Visitor visitor = new Visitor();
            visitor.visit(tree);
            visitor.print();
        }
    }
}
