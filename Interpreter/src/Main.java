import java.io.FileInputStream;
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
        FileInputStream inputStream = null;
        while (true) {
            if (inputStream != null) {
                if (!scanner.hasNextLine()) {
                    try {
                        inputStream.close();
                        scanner = new Scanner(System.in);
                        inputStream = null;
                    } catch (Exception err) {
                        System.err.println("Can't Close file!");
                    }
                }
            }
            String line = scanner.nextLine();

            while (line.contains(";") == false) {
                line = line + scanner.nextLine();
            }
            if (line.equals("quit;")) {
                CatalogManager.getInstance().WriteBack();
                return;
            }

            if (line.substring(0,4).equals("exec")) {
                line = line.substring(4);
                line = line.trim();
                line = line.replace(" ","");
                line = line.replace(";","");
                try {
                    inputStream = new FileInputStream(line);
                    scanner = new Scanner(inputStream);
                    continue;
                } catch (Exception err) {
                    System.err.println("Can't open file!");
                }
            }
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
                    Context.getInstance().push();
                   // visitor.print();
                    if (Context.getInstance().getError()) throw new Exception();
                } catch (Exception e) {
                    continue;
                }

            }
        }
    }
}
