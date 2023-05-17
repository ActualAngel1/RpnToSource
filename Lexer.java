import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Lexer(String source) {
        this.source = source;
    }

    List<Token> sourceToTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(Token.Type.EOF, " ", null));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '+': addToken(Token.Type.PLUS); break;
            case '-': addToken(Token.Type.MINUS); break;
            case '*': addToken(Token.Type.STAR); break;
            case '/': addToken(Token.Type.SLASH); break;
            case ' ', '\r', '\t': break;
            case '\n':
                line++;
                break;

            default:
                if (isDigit(c)) number();
                else throw new RuntimeException("The source text is invalid");
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void number() {
        while (isDigit(peek())) advance();

        addToken(Token.Type.NUMBER, Integer.parseInt(source.substring(start, current)));
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private void addToken(Token.Type type) {
        addToken(type, null);
    }

    private void addToken(Token.Type type, Integer number) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, line));
    }
}
