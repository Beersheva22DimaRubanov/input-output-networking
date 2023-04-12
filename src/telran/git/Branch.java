package telran.git;

import java.io.Serializable;

public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;
	private Commit lastCommit;
	private String name;

	public Branch(Commit lastCommit, String name) {
		super();
		this.lastCommit = lastCommit;
		this.name = name;
		}

	public Commit getLastCommit() {
		return lastCommit;
	}

	public String getName() {
		return name;
	}
	
	public void setLastCommit(Commit commit) {
		lastCommit = commit;
	}
}
