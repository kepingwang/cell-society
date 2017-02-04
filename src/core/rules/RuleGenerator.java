package core.rules;

public class RuleGenerator {
	
	public static Rule genRule(String gameName) {
		if (gameName.equals("game-of-life")) {
			return new GameOfLifeRule();
		} else {
			return null;
		}
	}
	
}
