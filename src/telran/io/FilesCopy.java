package telran.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesCopy extends Copy {

	public FilesCopy(String srcFilePath, String destFilePath, boolean overWrite) {
		super(srcFilePath, destFilePath, overWrite);
	}

	@Override
	long copy() {
		long res =  0;
		CopyOption option = null;
		try(InputStream input = new FileInputStream(srcFilePath)) {
			if(!overWrite && Files.exists(Path.of(destFilePath))) {
				throw new Exception();
			}
			res = Files.copy(input, Path.of(destFilePath), option);
		} catch (FileNotFoundException e) {
			System.out.println("File is not exist");
		} catch (Exception e) {
			System.out.println("Can't overwrire file");
		} 
		return res;
	}

}
