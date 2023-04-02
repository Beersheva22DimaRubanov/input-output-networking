package telran.view;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		StandartInputOutput st = new StandartInputOutput();
		Menu menu = createMenu();
		menu.perform(st);
	}

	private static Menu createMenu() {
		Item numberCal  = getNumberCal(); 
		Item dateOperations = getDateOperations();
    		return new Menu("Calculator", numberCal, dateOperations, Item.exit());
	}

	private static Item getDateOperations() {
		ArrayList<Item> items = new ArrayList<>();
		items.add(Item.of("Add days", t ->{
			LocalDate date = t.readDateISO("Enter date (yyyy-mm-dd)", "Wrong date");
			int days = t.readInt("Enter number of days", "Wrong number", 1, Integer.MAX_VALUE);
			t.writeLine("Your date: " + date.plusDays(days));
		}));
		items.add(Item.of("Minus days", t -> {
			LocalDate date = t.readDateISO("Enter date", "Wrong date");
			int days = t.readInt("Enter number of days", "Wrong number", 1, Integer.MAX_VALUE);
			t.writeLine("Your date: " + date.minusDays(days));
		}));
		items.add(Item.exit());
		return new Menu("Date operations", items);
	}

	private static Item getNumberCal() {
		ArrayList<Item> items = new ArrayList<>();
		items.add(Item.of("Sum ", t ->{
			double[] nums = getNumbers(t);
			t.writeLine("Sum of nums: " + (nums[0]+nums[1]));
		}));
		items.add(Item.of("Subtraction", t -> {
			double[] nums = getNumbers(t);
			t.writeLine("Substraction is: " + (nums[0]-nums[1]));
		}));
		items.add(Item.of("Multiplication", t -> {
			double[] nums = getNumbers(t);
			t.writeLine("Multiplication is: " + (nums[0]*nums[1]));
		}));
		items.add(Item.of("Division", t ->{
			double[] nums = getNumbers(t);
			while(nums[1] == 0) {
				t.writeLine("You can't divide by 0");
				nums[1] = t.readNumber("Type second num", "Must be a number");
			}
			t.writeLine("Division is: " + (nums[0]/nums[1]));
		}));
		items.add(Item.exit());
		return new Menu("Number calculator", items);
	}
	
	private static double[] getNumbers(InputOutput t) {
		double a = t.readNumber("Type first num", "Must be number");
		double b = t.readNumber("Type second num", "Must be a number");
		return new double[] {a, b};
	}
}
