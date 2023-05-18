public class Token {

    enum Type {
        MINUS, PLUS, SLASH, STAR,

        NUMBER,
        LEFTPAREN, RIGHTPAREN,
        BANG, BANG_EQUAL,
        EQUAL, EQUAL_EQUAL,
        GREATER, GREATER_EQUAL,
        LESS, LESS_EQUAL,

        EOF
    }

    final Type type;
    final String lexeme;
    final Integer value;


    Token(Type type, String lexeme, Integer value) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
    }

    public String toString() {
        return type + " " + value;
    }

}
