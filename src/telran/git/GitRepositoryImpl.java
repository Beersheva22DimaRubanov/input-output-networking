package telran.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GitRepositoryImpl implements GitRepository {
	private static final String GIT_FILE = ".mygit";
	private static final String MASTER = "master";
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, Commit> commits;
	private HashMap<String, Branch> branches;
	private Commit lastCommit;
	private String head;
	private static String path = ".";
	private Commit currentCommit;
	private String regexStr = ".*?(.classpath)|(.git)|(test.data)|(Employees.data)|(.settings)|(.gitignore)|(.project)";

	private GitRepositoryImpl() {
		commits = new LinkedHashMap<>();
		branches = new HashMap<>();
		head = null;
	}

	public static GitRepositoryImpl init() {
		GitRepositoryImpl res = null;
		File file = new File(GIT_FILE);
		if (file.exists()) {
			res = restore(GIT_FILE);
		} else {
			res = new GitRepositoryImpl();
		}
		return res;
	}

	private static GitRepositoryImpl restore(String path) {
		GitRepositoryImpl res = null;
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(GIT_FILE))) {
			res = (GitRepositoryImpl) input.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String commit(String commitMessage) {
		String res = null;
		if (head == null) {
			createBranch(MASTER);
		} else if (commits.containsKey(head)) {
			res = "Head should be on the branch";
		}
		List<FileState> filesStates = info();
		if (!isCommited(filesStates)) {
			setCommited(filesStates);
			Commit commit = new Commit(filesStates, commitMessage);
			lastCommit = currentCommit = commit;
			commits.put(commit.getName(), commit);
			branches.get(head).setLastCommit(commit);
			res = "commited";
		} else {
			res = "Nothing to commit";
		}
		return res;
	}

	private void setCommited(List<FileState> filesStates) {
		for (FileState file : filesStates) {
			file.setState(States.COMMITED);
		}
	}

	@Override
	public List<FileState> info() {
		List<FileState> res = new ArrayList<>();
		List<File> files = getFiles();
		for (File file : files) {
			if (lastCommit == null || !lastCommit.getFiles().containsKey(file.getPath())) {
				res.add(new FileState(file.getPath()));
			} else {
				checkStatus(lastCommit.getFiles().get(file.getPath()), file);
				res.add(lastCommit.getFiles().get(file.getPath()));
			}
		}
		return res;
	}

	private List<File> getFiles() {
		File dir = new File(path);
		File[] files = dir.listFiles();
		return Arrays.stream(files).filter(t -> !t.isDirectory())
				.filter(t -> !t.getName().matches(regexStr)).toList();
	}

	private void checkStatus(FileState fileState, File file) {
		Long modified = fileState.getLastModified();
		Long fileModefied = file.lastModified();
		if (fileModefied.compareTo(modified) > 0) {
			fileState.setState(States.MODIFIED);
			fileState.setLastModified(fileModefied);
		}
	}

	@Override
	public String createBranch(String branchName) {
		String res = "There are uncommited files";
		if (isCommited(info()) || branches.isEmpty()) {
			Branch branch = new Branch(lastCommit, branchName);
			branches.put(branchName, branch);
			head = branch.getName();
			res = "Branch " + branchName + " is created";
		}
		return res;
	}

	private boolean isCommited(List<FileState> states) {
		boolean res = true;
		for (FileState fs : states) {
			if (fs.getState().equals(States.MODIFIED) | fs.getState().equals(States.UNTRACKED)) {
				res = false;
			}
		}
		return res;
	}

	@Override
	public String renameBranch(String branchName, String newName) {
		String res = "wrong branch name";
		if (branches.containsKey(branchName)) {
			if (isCommited(info())) {
				branches.put(newName, branches.get(branchName));
				branches.remove(branchName);
				res = "Branch " + branchName + " has renamed to " + newName;
			} else {
				res = "there are uncommited files";
			}
		}
		return res;
	}

	@Override
	public String deleteBranch(String branchName) {
		String res = "wrong branch name";
		if (isBranch(branchName)) {
			if (isCommited(info())) {
				branches.remove(branchName);
				res = "Branch " + branchName + " was rdeleted";
			} else {
				res = "there are uncommited files";
			}
		}
		return res;
	}

	private boolean isBranch(String branchName) {
		return branches.containsKey(branchName);
	}

	@Override
	public List<CommitMessage> log() {
		List<CommitMessage> res = new ArrayList<>();
		commits.entrySet().stream().forEach(
				t -> res.add(new CommitMessage(t.getValue().getName(), t.getValue().getMessage())));
		return res;
	}

	@Override
	public List<String> branches() {
		return branches.keySet().stream().toList();
	}

	@Override
	public List<Path> commitContent(String commitName) {
		List<Path> res = new ArrayList<>();
		if (commits.containsKey(commitName)) {
			commits.get(commitName).getFiles().entrySet()
					.forEach(t -> res.add(Path.of(t.getValue().getPath())));
		}
		return res;
	}

	@Override
	public String switchTo(String name) {
		String res = "Wrong name";
		if (branches.containsKey(name) | commits.containsKey(name)) {

			if (!isCommited(info())) {
				res = "switch may be done only after commit";
			} else {
				List<File> files = getFiles();
				Map<String, byte[]> content = commits.get(name).getContent();
				Map<String, FileState> filesState = commits.get(name).getFiles();
				for (File file : files) {
					String filePath = file.getPath();
					file.delete();
					if (filesState.containsKey(filePath)) {
						try {
							Files.writeString(Path.of(filePath), new String(content.get(filePath)));
							currentCommit = commits.containsKey(name) ? commits.get(name)
									: branches.get(name).getLastCommit();
							filesState.remove(file.getPath());
							head = currentCommit.getName();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (!filesState.isEmpty()) {
					for (Entry<String, FileState> comitedFiles : filesState.entrySet()) {
						try {
							Files.writeString(Path.of(comitedFiles.getValue().getPath()),
									new String(content.get(comitedFiles.getValue().getPath())));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				List<FileState> filesStates = info();
				setCommited(filesStates);
			}
		}
		return res;
	}

	@Override
	public String getHead() {
		String res = "There is no branches and commits";
		if (head != null) {
			if (branches.containsKey(head)) {
				res = "Head on branch " + head;
			} else {
				res = "Head on commit " + head;
			}
		}
		return res;
	}

	@Override
	public void save() {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(GIT_FILE))) {
			output.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String addIgnoredFileNameExp(String str) {
		String res = "Wrong regex (entere fileType)";
		String regex = "^[.][a-z]*";
		if (regex.matches(str)) {
			if (regexStr == null) {
				regexStr = ".*?";
			}
			res = "added to ignore";
			regexStr += "(" + regex + ")|";
		}
		return res;
	}

}
