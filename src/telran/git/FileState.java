package telran.git;

import java.io.File;
import java.io.Serializable;

public class FileState implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private File file;
	private Long lastModified;
	private States state;

	public FileState(String path) {
		super();
		this.path = path;
		this.file = new File(path.toString());
		this.lastModified = file.lastModified();
		this.state = States.UNTRACKED;
	}

	public void setState(States state) {
		this.state = state;
	}

	public States getState() {
		return state;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		String name = path.replace(".\\", "");
		return "File = " + name + ", state=" + getState() + "\n";
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
}
