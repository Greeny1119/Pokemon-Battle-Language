public class Token {
    private TokenType type; // String, Integer
    private String value;
    private int line;

    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    	//Complete the constructor
        
    }

    public Token() {
        this.line = 1;
        //constructor
    }

    public TokenType getType() { 
        return type; 
    }
    public String getValue() { return value; }
    public int getLine() { return line; }

    @Override
    public String toString() {
    	//Complete the toString method
        return value;
    }
}