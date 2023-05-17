import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public final class RpnToAst {
    static public Expr convert(List<Token> tokens) {
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
                    Expr leftNode = stack.pop();
                    stack.push(new Expr.Binary(leftNode, token, rightNode));
                }
        }

        return stack.pop();
    }
}
