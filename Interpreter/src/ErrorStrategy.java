import org.antlr.v4.runtime.*;

/**
 * Created by tom on 15/10/28.
 */
public class ErrorStrategy extends DefaultErrorStrategy {
    @Override
    public void recover (Parser recognizer, RecognitionException e) {
        throw new RuntimeException(e);
    }

    @Override
    public Token recoverInline(Parser recognizer)
        throws RecognitionException
    {
        throw new RuntimeException(new InputMismatchException(recognizer));
    }

    @Override
    public void sync(Parser recognizer) {}

}
