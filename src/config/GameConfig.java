package config;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {
	private Map<String, String> configMap = new HashMap<String, String>();
	private int[][] layout;
	
	public GameConfig(Map<String, String> configMap, int[][] layout){
		this.configMap = configMap;
		this.layout = layout;
	}
	
	public Map<String, String> getConfigMap(){
		return configMap;
	}
	
	public int[][] getLayout(){
		return layout;
	}
}
