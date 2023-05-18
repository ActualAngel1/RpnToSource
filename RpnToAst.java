import java.util.*;

public final class RpnToAst {
    private static final HashMap<Token.Type, Integer> map = new HashMap<>();
    static {
        map.put(Token.Type.PLUS, 5);
        map.put(Token.Type.MINUS, 5);
        map.put(Token.Type.STAR, 4);
        map.put(Token.Type.SLASH, 4);
    }

    static public Expr convert(List<Token> tokens) {
        int current = 0;
        Stack<Expr> stack = new Stack<>();
        HashSet<Token.Type> set = new HashSet<>();
        set.add(Token.Type.MINUS);
        set.add(Token.Type.PLUS);
        set.add(Token.Type.SLASH);
        set.add(Token.Type.STAR);

        for (Token token : tokens) {
                if (token.type == Token.Type.EOF) break;
                if (!set.contains(token.type)) {
                    stack.push(new Expr.Literal(Integer.parseInt(token.lexeme)));
                } else {

                    if (stack.size() < 2) {
                        System.out.println();
                        System.out.println("This is not a valid RPN representation");
                        break;
                    }

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

                current++;
        }

        return stack.pop();
    }
}
