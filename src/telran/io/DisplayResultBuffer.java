package telran.io;

public class DisplayResultBuffer extends DisplayResult {
	long bufferSize;	
	
	public DisplayResultBuffer(long fileSize, long copyTime, long bufferSize) {
		super(fileSize, copyTime);
		this.bufferSize = bufferSize;
	}

	
	public String toString() {
		return "File size: " + getFileSize() + ", copy time: " + getCopyTime() + 
				", buffer size: " + bufferSize;
		
	}
}
