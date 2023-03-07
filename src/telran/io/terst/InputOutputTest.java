package telran.io.terst;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void printDirectoryFileTest() {
		printDirectoryFile(".", 0);
	}

	void printDirectoryFile(String path, int maxLevel) {
		int padding = 4;
		printDirectoryFile(path, maxLevel, padding);
	}

	private void printDirectoryFile(String path, int maxLevel, int offset) {
		File dir = new File(path);
		File[] filesArr = dir.listFiles();
		for (File file : filesArr) {
			String type = file.isDirectory() ? "Directory" : "File";
			System.out.println(" ".repeat(offset) + file.getName() + " - " + type);
			if (file.isDirectory() && maxLevel != 1) {
				printDirectoryFile(file.toString(), maxLevel - 1, offset + 4);
			}
		}
	}

	@Test
	void testFiles() {
		Path path = Path.of(".");
		System.out.println(path.toAbsolutePath().getNameCount());
	}

	@Test
	void printDirectoryFilesTest() {
		printDirectoryFiles(".", 4);
	}

	void printDirectoryFiles(String path, int maxLevel) {
		Stream<Path> str = null;
		try {
			if (maxLevel >= 1) {
				str = Files.walk(Path.of(path), maxLevel, FileVisitOption.FOLLOW_LINKS);
			} else {
				str = Files.walk(Path.of(path), FileVisitOption.FOLLOW_LINKS);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		str.forEach(
				x -> System.out.println(" ".repeat((x).getNameCount() * 3) + (x).getFileName()));
	}

}
