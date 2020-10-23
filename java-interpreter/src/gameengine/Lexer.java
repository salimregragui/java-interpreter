package gameengine;

import java.util.*;

enum TokenType {
	VERB, SYMBOL, LOCATOR, FOLLOW, END
}

class Token {
	TokenType type;
	String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
}

class Lexer {
	static List<String> authorizedVerbs = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{			
			add("get");
			add("go");
			add("eat");
		}
	};
	
	static List<String> authorizedLocators = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{			
			add("from");
		}
	};
	
	static List<Token> lexSentence(String sentence) {
		List<Token> listOfTokens = new ArrayList<Token>();
		
		String[] words = sentence.split("\\s+");
		List<String> wordsList = new ArrayList<String>();
		wordsList = Arrays.asList(words);
		
		for (String word:wordsList) {
			if (authorizedVerbs.contains(word)) {
				listOfTokens.add(new Token(TokenType.VERB, word));
			} else if(authorizedLocators.contains(word)) {
				listOfTokens.add(new Token(TokenType.LOCATOR, word));
			} else {
				listOfTokens.add(new Token(TokenType.SYMBOL, word));
			}
		}
		
		listOfTokens.add(new Token(TokenType.END, "END"));
		
		return listOfTokens;
	}
}
