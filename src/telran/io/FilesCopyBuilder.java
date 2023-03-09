package telran.io;

public class FilesCopyBuilder {
	public static Copy build(String type, String[] args) {
		switch (type) {
		case "Transfer":
			return new TransferCopy(args[0], args[1], false);
		case "Buffer":
			return new BufferCopy(args[0], args[1], true, 1024 * 1024);
		case "FilesCopy":
			return new FilesCopy(args[0], args[1], false);
		default:
			return null;
		}
	}

	public static void main(String[] args) {
		BufferCopy bufferCopy = (BufferCopy) build("Buffer",
				new String[] { "test.pdf", "test-copy.pdf" });
		bufferCopy.copyRun();
	}
}
