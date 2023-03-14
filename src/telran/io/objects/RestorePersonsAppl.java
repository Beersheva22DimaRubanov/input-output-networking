package telran.io.objects;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RestorePersonsAppl {

	public static void main(String[] args) throws Exception {
		Persons persons = null;
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("persons.data"))) {
			persons = (Persons) input.readObject();
			System.out.println(persons.persons);
		}
	}
}
