package gameengine;

import java.util.*;

class Tree {
	int position;
	String action;
	String left;
	String right;
	
	public Tree() {
		
	}
	
	public Tree(String action) {
		this.action = action;
	}
	
	public void setPosition(int pos) {
		this.position = pos;
	}
	
	public void setLeft(String left) {
		this.left = left;
	}
	
	public void setRight(String right) {
		this.right = right;
	}
	
	public String getAction() {
		return action;
	}
}

class ParseTree {
	ArrayList<Tree> trees = new ArrayList<Tree>();
	
	public void addTree(Tree t) {
		trees.add(t);
	}
	
	public ArrayList<Tree> getTrees() {
		return this.trees;
	}
}

class Parser {
	String error = "";
	ParseTree ast = new ParseTree();
	
	String getError() {
		return error;
	}
	
	List<Tree> getParseTree() {
		return ast.getTrees();
	}
	
	void parse(List<Token> tokens) {
		int totalActions = 0;
		int currentPosition = 0;
		
		for (Token t: tokens) {
			if(t.type == TokenType.FOLLOW) {
				totalActions++;
			}
		}
		
		currentPosition = 1;
		TokenType expecting = null;
		Tree newTree = null;
		boolean makingTree = true;
		boolean makingLocator = false;
		
		for (Token t: tokens) {
			if (!makingTree || (t.type == TokenType.END && currentPosition <= totalActions)) {
				this.ast.addTree(newTree);
				currentPosition++;
				makingTree = true;
				System.out.println("-------------saved tree-----------------");
			}
			
			if (t.type == TokenType.VERB) {
				if (expecting != null) {
					this.error = "action " + newTree.action + " doesn't expect another action as it's parameter.";
					break;
				}else {
					newTree = new Tree(t.value);
					newTree.setPosition(currentPosition);
					expecting = TokenType.SYMBOL;
					makingTree = true;
					System.out.println("-------------starting tree-----------------");
				}
			}
			
			if (t.type == TokenType.SYMBOL && expecting == TokenType.SYMBOL) {
				if (makingTree && !makingLocator) {					
					newTree.setLeft(t.value);
					System.out.println("-------------added object-----------------");
					System.out.println(t.value);
					expecting = null;
				} else if (makingTree && makingLocator) {
					newTree.setRight(t.value);
					System.out.println("-------------added locator-----------------");
					System.out.println(t.value);
					expecting = null;
					makingTree = false;
					makingLocator = false;
				} else {
					this.error = "object " + t.value + " cannot be used as an action.";
					break;
				}
			}
			
			if (t.type == TokenType.LOCATOR) {
				if (expecting != TokenType.SYMBOL && makingTree) {					
					expecting = TokenType.SYMBOL;
					makingLocator = true;
				} else if (expecting == TokenType.SYMBOL && makingTree) {
					this.error = t.value + " cannot be used as an object.";
					break; 
				} else {
					this.error = t.value + " cannot be used as an action.";
					break;
				}
			}
		}
	}
}
