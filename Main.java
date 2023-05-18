import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException{
        runPrompt();
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("-> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }

    }

    private static void run(String rpn) {
        Lexer lexer = new Lexer(rpn);
        List<Token> tokens = lexer.sourceToTokens();

        printTokens(tokens);

        Expr ast = RpnToAst.convert(tokens);

        printAst(ast);

        printSource(ast);
    }

    public static void print(String string) {
        System.out.println();
        System.out.println(string);
        System.out.println();
    }

    public static void printTokens(List<Token> tokens) {
        print("Printing the tokens: ");

        for (Token token : tokens) {
            System.out.println("Token: " +
                    ((Objects.equals(token.lexeme, " ")) ? "EOF" : token.lexeme) +
                    " " + token.type);
        }
    }

    public static void printAst(Expr ast) {
        print("Printing the ast");

        AstPrinter printer = new AstPrinter();
        System.out.println(printer.print(ast));
    }

    public static void printSource(Expr ast) {
        print("Restructuring source code");

        System.out.println(new AstToSource().transform(ast));
    }
}