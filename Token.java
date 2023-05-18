public class Token {

    enum Type {
        MINUS, PLUS, SLASH, STAR,

        NUMBER,
        LEFTPAREN, RIGHTPAREN,

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
