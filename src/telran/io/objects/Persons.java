package telran.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Persons implements Serializable, Iterable<Person> {
	static String filePath = "persons.data";
	private static final long serialVersionUID = 1L;
	List<Person> persons = new ArrayList<>();

	void addPerson(Person person) {
		persons.add(person);
	}

	@Override
	public Iterator<Person> iterator() {
		return persons.iterator();
	}
	
	public void save() {
		try {
			writeObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restore() {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))){
			readObject(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
		input.defaultReadObject();
	}

	private void writeObject() throws Exception {
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))){
			output.defaultWriteObject();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
