package telran.git;

public class CommitMessage {
	private String commitName;
	private String message;

	public CommitMessage(String commitName, String message) {
		super();
		this.commitName = commitName;
		this.message = message;
	}

	@Override
	public String toString() {
		return "commitName=" + commitName + ", message=" + message + "\n";
	}

}
