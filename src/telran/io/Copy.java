package telran.io;

abstract public class Copy {
	String srcFilePath;
	String destFilePath;
	boolean overWrite;

	public Copy(String srcFilePath, String destFilePath, boolean overWrite) {
		super();
		this.srcFilePath = srcFilePath;
		this.destFilePath = destFilePath;
		this.overWrite = overWrite;
	}

	abstract long copy();

	public DisplayResult getDisplayResult(long copyTime, long fileSize) {
		return new DisplayResult(fileSize, copyTime);
	}

	void copyRun() {
		long time = System.currentTimeMillis();
		long fileSize = copy();
		DisplayResult res = getDisplayResult(fileSize, (System.currentTimeMillis() - time));
		System.out.println(res.toString());
	}
}
