package cellsociety;

public class GameOfLifeRules implements Rule {

	@Override
	public int update(Cell cell, Cell[] neighbors) {
		// TODO Auto-generated method stub
		int cellState = cell.getState();
		int numLiving = 0;
		for(int i = 0; i < neighbors.length; i++){
			if (neighbors[i] != null) {
				numLiving += neighbors[i].getState(); 
			}
		}
		if(cellState == 1 && (numLiving < 2 || numLiving > 3)){
			cellState = 0;
		}
		else if(cellState == 0 && numLiving == 3){
			cellState = 1;
		}
		return cellState;
	}

}
