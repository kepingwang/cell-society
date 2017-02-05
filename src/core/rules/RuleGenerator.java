package core.rules;

public class RuleGenerator {
	
	public static Rule genRule(String gameName) {
		if (gameName.equals("game-of-life")) {
			return new GameOfLifeRule();
		}
		if (gameName.equals("spreading-of-fire")){
			return new FireRule();
		}
//		if (gameName.equals("segregation")){
//			return new Segregation();
//		}
		else {
			return null;
		}
	}
	
}
