package telran.sportsmen;

public class Runner implements Sportsman {
	int distance;
	
	public Runner() {
		
	}
	
	public Runner (String distance) {
		this.distance = Integer.parseInt(distance);
	}
	
	@Override
	public void action() {
		System.out.printf("runs distance %s\n", distance != 0? distance + " " + "km": "");
	}

}
