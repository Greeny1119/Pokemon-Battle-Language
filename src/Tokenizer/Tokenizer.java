import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.regex.Pattern;

public class Tokenizer {
	private static final Pattern OPSREGX = Pattern.compile("/^(\\+|-|\\*|\\/|=|>|<|>=|<=|&|\\||%|!|\\^|\\(|\\))$/");
	
	public static List<Token> tokenize(File inputFile) {
		int linenumber = 0; //initilize the line numerb
		
		List<Token> tokens = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		

	    try (BufferedReader filereader = new BufferedReader(new FileReader(inputFile))) {	    
			String line; //the line for each part of the code
			while ((line = filereader.readLine()) != null) {
				linenumber+=1; //increase the line number for each iteration
			
				String word = null;
				//reset for future loops

				for (int i = 0; i>=line.length()-1; i++) {
					word = word + line.charAt(i); //add the char that was looked at
					if (Character.isWhitespace(line.charAt(i))) { //the next char is whitespace
						//turn this into function?
						word = word.substring(1); //cut off the most recent thing (the whitespace)
						errors.add(Createtoken(word, linenumber, tokens));
					} 
					
					else if (OPSREGX.matcher(Character.toString(line.charAt(i))).find()
					&& !(OPSREGX.matcher(word.substring(1)).find() //the previous string does not contain on operator
					&& (word.length() > 1))  /*valid to be no space (operator)*/) { //length greater then 1 i=1
						
						word = word.substring(1); //cutoff the most recent digit
						errors.add(Createtoken(word, linenumber, tokens));

					} else if (OPSREGX.matcher(Character.toString(line.charAt(i))).find() && !OPSREGX.matcher(Character.toString(line.charAt(i+1))).find()) /*valid to be operator*/{
						errors.add(Createtoken(word, linenumber, tokens));
					} // for i+1 it will send the +, but will wait another cycle if the next char is an op

					else if (line.charAt(i) == '(' || line.charAt(i) == ')') {
						errors.add(Createtoken(word, linenumber, tokens));
					} //needed for () or else it would send both individually

					else { 
						
					}
				
	  	 		}
			}
		} catch (IOException e) {
			System.err.println("file not found");
		} 

		errors.removeAll(Collections.singleton(null));
		if (!errors.isEmpty()) {
			for (String elem : errors) {
				System.out.println(elem);
			}
		}
	
	    return tokens;

	    
	    //return the tokens in the input code	    
	}
	private static final String[] tokenpatterns = {
		"Pokemon", // Pokemon Keyword
		"int", // int keyword
		"String", // String keyword
		"boolean", // boolean keyword
		"function",
		"void",
		"elseif",
		"else",
		"show",
		"spelldatabase",
		"move1",
		"move2",
		"move3",
		"move4",
		"continue",
		"break",
		"return",
		"print",
		"while",
		"if",
		"for",
		"true",
		"false",
		"[a-zA-Z_]([a-zA-Z_]|[0-9])*", // Identifiers
		"0|[1-9][0-9]*", // Int Literal
		"\"[^\"]*\"", // String Literals
		"=", // Assignment operator
		"==", // Compare Operator
		"\\+\\+",
		"--",
		"<",  // Less than
		">",  // Greater than
		"<=", // Less than or equal
		">=", // Greater than or equal
		"\\+=", // Add-and-assign operator
		"-=", // Subtract-and-assign operator
		"\\+", // Addition operator
		"-", // Subtraction operator
		"\\*", // Multiply operator
		"/", // Divide operator
		"&&", // Logical And operator
		"\\|\\|", // Logical Or operator
		"!", // Logical Not operator
		"!=", // Compare operator
		"\\(", // Left Parenthesis
		"\\)", // Right Parenthesis
		"\\{", // Left Bracket
		"\\}", // Right Bracket
		";", // Statement Terminator
		",", // Parameter separator
		"\\.", // Field access operator
		"\\z" // EOF
	};

	private static final Map<String, TokenType> TOKENTYPE_MAP = Map.ofEntries(
		entry("Pokemon", TokenType.KW_POKEMON),
        entry("int", TokenType.KW_INT),
        entry("String", TokenType.KW_STRING),
        entry("boolean", TokenType.KW_BOOLEAN),
        entry("function", TokenType.KW_FUNCTION),
        entry("void", TokenType.KW_VOID),
        entry("elseif", TokenType.KW_ELSEIF),
        entry("else", TokenType.KW_ELSE),
        entry("show", TokenType.KW_SHOW),
        entry("spelldatabase", TokenType.KW_SPELLDATABASE),
        entry("move1", TokenType.KW_MOVE1),
        entry("move2", TokenType.KW_MOVE2),
        entry("move3", TokenType.KW_MOVE3),
        entry("move4", TokenType.KW_MOVE4),
        entry("continue", TokenType.KW_CONTINUE),
        entry("break", TokenType.KW_BREAK),
        entry("return", TokenType.KW_RETURN),
        entry("print", TokenType.KW_PRINT),
        entry("while", TokenType.KW_WHILE),
        entry("if", TokenType.KW_IF),
        entry("for", TokenType.KW_FOR),

        // Literals and Identifiers
        entry("true", TokenType.OP_TRUE),
        entry("false", TokenType.OP_FALSE),
        entry("[a-zA-Z_]([a-zA-Z_]|[0-9])*", TokenType.IDENTIFIER),
        entry("0|[1-9][0-9]*", TokenType.INT_LITERAL),
        entry("\"[^\"]*\"", TokenType.STRING_LITERAL),

        // Operators and Symbols
        entry("=", TokenType.OP_ASSIGN),
        entry("==", TokenType.OP_EQUALS),
        entry("\\+\\+", TokenType.OP_INCREMENT),
        entry("--", TokenType.OP_DECREMENT),
        entry("<", TokenType.OP_LESS_THAN),
        entry(">", TokenType.OP_GREATER_THAN),
        entry("<=", TokenType.OP_LESS_THAN_EQUALS),
        entry(">=", TokenType.OP_GREATER_THAN_EQUALS),
        entry("\\+=", TokenType.OP_PLUS_ASSIGN),
        entry("-=", TokenType.OP_MINUS_ASSIGN),
        entry("\\+", TokenType.OP_PLUS),
        entry("-", TokenType.OP_MINUS),
        entry("\\*", TokenType.OP_MULTIPLY),
        entry("/", TokenType.OP_DIVIDE),
        entry("&&", TokenType.OP_AND),
        entry("\\|\\|", TokenType.OP_OR),
        entry("!", TokenType.OP_NOT),
        entry("!=", TokenType.OP_NOTEQUAL),
        entry("\\(", TokenType.LPAREN),
        entry("\\)", TokenType.RPAREN),
        entry("\\{", TokenType.LBRACE),
        entry("\\}", TokenType.RBRACE),
        entry(";", TokenType.SEMICOLON),
        entry(",", TokenType.COMMA),
        entry("\\.", TokenType.DOT),
        entry("\\z", TokenType.EOF)
    );
    private static TokenType gettokentype(String string) {
		for (String elem : tokenpatterns) {
			Pattern pattern = Pattern.compile(elem);
			boolean found = pattern.matcher(string).find();
			if (found) {
				return TOKENTYPE_MAP.get(elem);
			}
		}
		return null;
    }


	private static String Createtoken(String word,int linenumber, List<Token> tokens) {
		if (word.length() == 0) {

		} else {//skip if it found whitespace first
			TokenType tokentype = gettokentype(word); //figure out what kind of token it is
			if (tokentype != null) { //if we found a token
			tokens.add(new Token(tokentype, word, linenumber));
			} else {
				String error = "Invalid token at line: " + linenumber;
				//code
				return error;
			//report error
			}
		}
		return null;
	}
}