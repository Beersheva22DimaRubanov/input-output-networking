package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);

	void writeString(Object obj);

	default void writeLine(Object obj) {
		writeString(obj.toString() + "\n");
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		boolean running = true;
		R result = null;
		while (running) {
			try {
				String str = readString(prompt);
				result = mapper.apply(str);
				running = false;
			} catch (Exception e) {
				writeLine(errorPrompt + " - " + e.getMessage());
			}
		}
		return result;
	}

	default String readStringPredicate(String prompt, String errorPrompt,
			Predicate<String> predicate) {
		Function<String, String> function = t -> {
			if (!predicate.test(t)) {
				throw new RuntimeException();
			}
			return t;
		};
		return readObject(prompt, errorPrompt, function);
	}

	default String readStringOptions(String prompt, String errorPrompt, Set<String> options) {
		Function<String, String> function = t -> {
			if (!options.contains(t)) {
				throw new RuntimeException("Wrong - " + prompt);
			}
			return t;
		};
		return readObject(prompt, errorPrompt, function);
	}

	default int readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	default int readInt(String prompt, String errorPrompt, int min, int max) {
		Function<String, Integer> function = t -> {
			try {
				int res = Integer.parseInt(t);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("must be a number");
			}
		};
		return readObject(prompt, errorPrompt, function);
	}

	default void checkRange(double min, double max, double res) {
		if (res > max || res < min) {
			throw new RuntimeException("Num should be in a range: " + min + " - " + max);
		}
	}

	default long readLong(String prompt, String errorPrompt, long min, long max) {
		Function<String, Long> function = t -> {
			try {
				long res = Long.parseLong(t);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("must be a number");
			}
		};
		return readObject(prompt, errorPrompt, function);
	}

	default LocalDate readDateISO(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, LocalDate::parse);
	}

	default LocalDate readDate(String prompt, String errorPrompt, String format, LocalDate min,
			LocalDate max) {
		Function<String, LocalDate> function = t -> {
			DateTimeFormatter formatter = null;
			try {
				formatter = DateTimeFormatter.ofPattern(t);
			} catch (Exception e) {
				throw new RuntimeException("Wrong date format " + format);
			}
			LocalDate date = LocalDate.parse(t, formatter);
			if (date.isAfter(max)|| date.isBefore(min)) {
				throw new RuntimeException("Date must be in range: " + min + " - " + max);
			}
			return date;
		};
		return readObject(prompt, errorPrompt, function);
	}
	
	default double readNumber(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Double::parseDouble);
	}

	default double readNumber(String prompt, String errorPrompt, double min, double max) {
		Function<String, Double> function = t -> {
			try {
				double res = Double.parseDouble(t);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("must be a number");
			}
		};
		return readObject(prompt, errorPrompt, function);
	}
}