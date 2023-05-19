import java.util.*;

public final class RpnToAst {
    private static final Map<Token.Type, Integer> map;

    static {
         /*
                PREC_NONE,
                PREC_ASSIGNMENT,  // =
                PREC_OR,          // or
                PREC_AND,         // and
                PREC_EQUALITY,    // == !=
                PREC_COMPARISON,  // < > <= >=
                PREC_TERM,        // + -
                PREC_FACTOR,      // * /
                PREC_UNARY,       // ! -
                PREC_CALL,        // . ()
                PREC_PRIMARY
         */
        map = new HashMap<>();
        map.put(Token.Type.EQUAL, 10);
        map.put(Token.Type.BANG_EQUAL, 6);
        map.put(Token.Type.EQUAL_EQUAL, 6);
        map.put(Token.Type.GREATER_EQUAL, 5);
        map.put(Token.Type.LESS_EQUAL, 5);
        map.put(Token.Type.GREATER, 5);
        map.put(Token.Type.LESS, 5);
        map.put(Token.Type.PLUS, 4);
        map.put(Token.Type.MINUS, 4);
        map.put(Token.Type.STAR, 3);
        map.put(Token.Type.SLASH, 3);
        map.put(Token.Type.BANG, 2);
    }

    static public Expr convert(List<Token> tokens) {
        Stack<Expr> stack = new Stack<>();

        for (Token token : tokens) {
            if (token.type == Token.Type.EOF) break;

            else if (!map.containsKey(token.type)) {
                literal(stack, token);
            } else if (isUnary(stack, token)) {
                unary(stack, token);
            } else {
                binary(stack, token);
            }
        }

        return stack.pop();
    }

    private static void literal(Stack<Expr> stack, Token token) {
        stack.push(new Expr.Literal(Integer.parseInt(token.lexeme)));
    }

    private static boolean isUnary(Stack<Expr> stack, Token token) {
        return (token.type == Token.Type.BANG) ||
                    ((token.type == Token.Type.MINUS) && (stack.size() == 1));
    }

    private static void unary(Stack<Expr> stack, Token token) {
        stack.push(new Expr.Unary(token, stack.pop()));
    }

    private static void binary(Stack<Expr> stack, Token token) {
        if (stack.size() < 2) {
            throw new RuntimeException("This is not a valid RPN Representation");
        }

        // Applying grouping inferring on left and right nodes
        Expr rightNode = stack.pop();
        if (rightNode instanceof Expr.Binary rightExpr) {
            if (map.get(rightExpr.operator.type) > map.get(token.type)) {
                rightNode = new Expr.Grouping(rightNode);
            }
        }

        Expr leftNode = stack.pop();
        if (leftNode instanceof Expr.Binary leftExpr) {
            if (map.get(leftExpr.operator.type) > map.get(token.type)) {
                leftNode = new Expr.Grouping(leftNode);
            }
        }

        stack.push(new Expr.Binary(leftNode, token, rightNode));
    }

}
