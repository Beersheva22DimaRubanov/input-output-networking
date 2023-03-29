package telran.view;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		StandartInputOutput st = new StandartInputOutput();
		Menu menu = madeMenu();
		menu.perform(st);
	}

	private static Menu madeMenu() {
		Item numberCal  = getNumberCal(); 
		Item dateOperations = getDateOperations();
		ArrayList<Item> items = new ArrayList<>();
		items.add(numberCal);
		items.add(dateOperations);
		items.add(Item.exit());
		return new Menu("Calculator", items);
	}

	private static Item getDateOperations() {
		ArrayList<Item> items = new ArrayList<>();
		items.add(Item.of("Add days", t ->{
			LocalDate date = t.readDateISO("Enter date", "Wrong date");
			int days = t.readInt("Enter number of days", "Wrong number");
			t.writeLine("Your date: " + date.plusDays(days));
		}));
		items.add(Item.of("Minus days", t -> {
			LocalDate date = t.readDateISO("Enter date", "Wrong date");
			int days = t.readInt("Enter number of days", "Wrong number");
			t.writeLine("Your date: " + date.minusDays(days));
		}));
		items.add(Item.exit());
		return new Menu("Date operations", items);
	}

	private static Item getNumberCal() {
		ArrayList<Item> items = new ArrayList<>();
		items.add(Item.of("Sum ", t ->{
			int a = t.readInt("Type first num", "Must be number");
			int b = t.readInt("Type second num", "Must be a number");
			t.writeLine("Sum of nums: " + (a+b));
		}));
		items.add(Item.of("Subtraction", t -> {
			int a = t.readInt("Type first num", "Must be number");
			int b = t.readInt("Type second num", "Must be a number");
			t.writeLine("Substraction is: " + (a-b));
		}));
		items.add(Item.of("Multiplication", t -> {
			int a = t.readInt("Type first num", "Must be number");
			int b = t.readInt("Type second num", "Must be a number");
			t.writeLine("Multiplication is: " + (a*b));
		}));
		items.add(Item.of("Division", t ->{
			int a = t.readInt("Type first num", "Must be number");
			int b = t.readInt("Type second num", "Must be a number");
			t.writeLine("Division is: " + (a/b));
		}));
		items.add(Item.exit());
		return new Menu("Number calculator", items);
	}
}
