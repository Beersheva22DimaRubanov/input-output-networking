package telran.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransferCopy extends Copy {

	public TransferCopy(String srcFilePath, String destFilePath, boolean overWrite) {
		super(srcFilePath, destFilePath, overWrite);
	}

	@Override
	long copy() {
		long res = 0;
		try(InputStream input = new FileInputStream(srcFilePath);
				OutputStream output = new FileOutputStream(destFilePath)){
			if(!overWrite && Files.exists(Path.of(destFilePath))) {
				throw new Exception();
			}
			res = input.transferTo(output);
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
		} catch (Exception e) {
			System.out.println("Can't overwrire file");;
		}
		return res;
	}
}
