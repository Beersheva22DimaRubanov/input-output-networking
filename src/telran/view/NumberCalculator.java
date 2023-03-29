package telran.view;

import java.util.ArrayList;

public class NumberCalculator extends Menu {

	private static String name;
	private static ArrayList<Item> items;
	
	
	public NumberCalculator() {
		super(name, items);
		name = "Number calculator";
		items = new ArrayList<>();
		addItems(items);
	}
	
	private void addItems (ArrayList<Item> items) {
		StandartInputOutput st = new StandartInputOutput();
		items.add(Item.of("Sum ", t ->{
			int a = st.readInt("Type first num", "Must be number");
			int b = st.readInt("Type second num", "Must be a number");
			st.writeLine("Sum of nums: " + (a+b));
		}));
		items.add(Item.of("Subtraction", t -> {
			int a = st.readInt("Type first num", "Must be number");
			int b = st.readInt("Type second num", "Must be a number");
			st.writeLine("Substraction is: " + (a-b));
		}));
		items.add(Item.of("Multiplication", t -> {
			int a = st.readInt("Type first num", "Must be number");
			int b = st.readInt("Type second num", "Must be a number");
			st.writeLine("Multiplication is: " + (a*b));
		}));
		items.add(Item.of("Division", t ->{
			int a = st.readInt("Type first num", "Must be number");
			int b = st.readInt("Type second num", "Must be a number");
			st.writeLine("Division is: " + (a/b));
		}));
		
	}

}
