package telran.git;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commit implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String name;
	private String message;
	private String branch;
	private Map<String, byte[]> content;
	private Map<String, FileState> files;

	public Commit(List<FileState> files, String message, String branch) {
		super();
		this.branch = branch;
		this.name = String.valueOf(this.hashCode());
		this.message = message;
		this.files = new HashMap<>();
		files.forEach(t-> this.files.put(t.getPath().toString(), t));
		content = new HashMap<>();
		files.forEach(t-> content.put(t.getPath().toString(), getBytes(Path.of(t.getPath()))));
	}

	private byte[] getBytes(Path path) {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public String getName() {
		return name;
	}
	
	public Map<String, FileState> getFiles(){
		return files;
	}

	public Map<String, byte[]> getContent() {
		return content;
	}

	public String getMessage() {
		return message;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}
