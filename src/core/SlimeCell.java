package core;

public class SlimeCell extends Cell {
	
	private double wiggleBias;
	private double wiggleAngle;
	private double sniffThreshold;
	private double sniffAngle;
	private double cAMPStrength;
	
	public SlimeCell(double x, double y, double w, double h, int state){
		super(x,y,w,h,state);
		wiggleBias = 0;
		wiggleAngle = 40;
		sniffThreshold = 1.0;
		sniffAngle = 45;
		
		cAMPStrength=2;
	}
	
	public double getWiggleBias(){
		return wiggleBias;
	}
	
	public double getWiggleAngle(){
		return wiggleAngle;
	}
	
	public double getSniffThreshold(){
		return sniffThreshold;
	}
	
	public double getSniffAngle(){
		return sniffAngle;
	}
	
	public double getCAMPStrength(){
		return cAMPStrength;
	}
	
	public void decay(){
		cAMPStrength--;
	}
}
