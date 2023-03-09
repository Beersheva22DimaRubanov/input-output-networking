package telran.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferCopy extends Copy {
	private long bufferSize;

	public BufferCopy(String srcFilePath, String destFilePath, boolean overWrite, long bufferSize) {
		super(srcFilePath, destFilePath, overWrite);
		this.bufferSize = bufferSize;
	}

	@Override
	long copy() {
		long res =  0;
		try(InputStream input = new FileInputStream(srcFilePath);
				OutputStream output = new FileOutputStream(destFilePath)) {
			if(!overWrite && Files.exists(Path.of(destFilePath))) {
				throw new Exception();
			}
			int n =0;
			byte[] buffer = new byte[(int) bufferSize];
			while( (n = input.read(buffer)) > 0) {
				output.write(buffer, 0, n);
				res += n;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File is not exist");
		} catch (Exception e) {
			System.out.println("Can't overwrite file");
		} 
		return res;
	}

	@Override
	public DisplayResult getDisplayResult(long copyTime, long fileSize) {
		return new DisplayResultBuffer(copyTime, fileSize, bufferSize);
	}

}
