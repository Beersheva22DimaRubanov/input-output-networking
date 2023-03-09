package telran.io;

public class DisplayResult {
	private long fileSize;
	private long copyTime;

	public DisplayResult(long fileSize, long copyTime) {
		super();
		this.fileSize = fileSize;
		this.copyTime = copyTime;
	}
	
	public String toString() {
		return "File size: " + fileSize + " copy time: " + copyTime;
	}

	public long getCopyTime() {
		return copyTime;
	}

	public long getFileSize() {
		return fileSize;
	}


}
