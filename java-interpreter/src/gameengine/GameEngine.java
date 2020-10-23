package gameengine;

import java.util.Scanner;

public class GameEngine {
	boolean playing;
	String output;
	String input;
	
	public GameEngine() {
		this.playing = false;
		this.output = "";
	}
	
	public void initialize() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Start playing ? ");
		String choice = sc.nextLine();
		
		if (choice.equals("yes")) {	
			this.playing = true;
			this.gameLoop();
		} else {
			System.out.println("The game is stopped before starting !");
		}
		
		sc.close();
	}
	
	public void gameLoop() {
		Scanner sc = new Scanner(System.in);
		Parser parser = new Parser();
		
		while(this.playing) {
			this.input = sc.nextLine();
			for(Token t:Lexer.lexSentence(input)) {
				System.out.print(t.type + " | ");
			}
			System.out.print("\n");
			parser.parse(Lexer.lexSentence(input));
			
			if(!parser.getError().isEmpty()) {
				System.out.println(parser.getError());
			}
		}
		
		sc.close();
	}
	
	public void startGame() {
		this.playing = true;
		this.output = "------------ THE GAME IS STARTED ------------";
	}
	
	public void stopGame() {
		this.playing = false;
		this.output = "------------ THE GAME IS STOPPED ------------";
	}
}
