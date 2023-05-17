public class AstToSource {
    Expr ast;
    String source = "";

    public AstToSource(Expr expr) {
        this.ast = expr;
        this.source = reconstructSource(ast);
    }

    public String getSource() {
        return this.source;
    }

    public String reconstructSource(Expr expr) {
        if (expr instanceof Expr.Binary binaryExpr) {
            Expr leftOperand = binaryExpr.left;
            Expr rightOperand = binaryExpr.right;
            Token operator = binaryExpr.operator;

            String leftSource = reconstructSource(leftOperand);
            String rightSource = reconstructSource(rightOperand);

            return leftSource + " " + operator.lexeme + " " + rightSource;

        } else if (expr instanceof Expr.Literal literal) {
            return Integer.toString(literal.value);
        }

        return "";
    }




}
