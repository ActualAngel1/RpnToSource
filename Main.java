import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    private static void run(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.sourceToTokens();

        System.out.println("Printing the tokens: ");

        for (Token token : tokens) {
            System.out.println("Token: " + ((token.lexeme == " ") ? "EOF" : token.lexeme) + " " + token.type);
        }

        new RpnToAst();
        Expr ast = RpnToAst.convert(tokens);

        System.out.println();
        System.out.println("Printing the ast");
        System.out.println();

        AstPrinter printer = new AstPrinter();
        System.out.println(printer.print(ast));

        System.out.println();
        System.out.println("Restructuring source code");
        System.out.println();

        AstToSource astToSource = new AstToSource(ast);
        System.out.println(astToSource.getSource());
    }
}