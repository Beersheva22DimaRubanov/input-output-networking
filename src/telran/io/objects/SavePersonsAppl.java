package telran.io.objects;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SavePersonsAppl {

	public static void main(String[] args) throws Exception {
		Person person = new Person(123, "Vasya");
		Person person2 = new Person(124, "Petya");
		person.person = person2;
		person2.person = person;
		Persons persons = new Persons();
		persons.addPerson(person);
		persons.addPerson(person2);
//		try (ObjectOutputStream output = new ObjectOutputStream(
//				new FileOutputStream("persons.data"))) {
//			output.writeObject(persons);
//		}
	}
}
