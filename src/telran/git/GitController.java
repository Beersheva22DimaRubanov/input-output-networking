package telran.git;

import java.nio.file.Path;
import java.util.List;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class GitController {
	private static GitRepositoryImpl git;
	
	private GitController() {
	}
	
	public static Item getGitItems(GitRepositoryImpl git) {
		GitController.git = git;
		return getUserMenu();
	}
	
	private static Item getUserMenu() {
		return new Menu("git menu", new Item[] {
				Item.of("Commit", GitController::commit),
				Item.of("Get info", GitController::info),
				Item.of("Create branch", GitController::createBranch),
				Item.of("Rename branch", GitController::renameBranch),
				Item.of("Delete branch", GitController::deleteBranch),
				Item.of("Log", GitController::log),
				Item.of("Branches", GitController::branches),
				Item.of("Commit content", GitController::commitContent),
				Item.of("Switch to", GitController::switchTo),
				Item.of("Get head", GitController::getHead),
				Item.of("Add to ignore", GitController::addToIgnore),
				Item.of("Exit", GitController::save, true)
				
		});
	}
	
	private static void commit(InputOutput io) {
		String message = io.readString("Entere commit message");
		git.commit(message);
	}
	
	private static void addToIgnore(InputOutput io) {
		String message = io.readString("Entere file type to ignore");
		io.writeLine(git.addIgnoredFileNameExp(message));
	}
	
	private static void save(InputOutput io) {
		git.save();
	}
	
	private static void info(InputOutput io) {
		List<FileState> fileStates = git.info();
		if(fileStates.isEmpty()) {
			io.writeLine("There is no files");
		} else {
			io.writeLine(git.info());
		}
	}
	
	private static void createBranch(InputOutput io) {
		String branchName = io.readString("Branch name");
		io.writeLine(git.createBranch(branchName));
	}
	
	private static void renameBranch(InputOutput io) {
		String branchName = io.readString("Branch name");
		String newBranchName = io.readString("New name");
		io.writeLine(git.renameBranch(branchName, newBranchName));
	}
	
	private static void deleteBranch(InputOutput io) {
		String branchName = io.readString("Branch name");
		io.writeLine(git.deleteBranch(branchName));
	}
	
	private static void log(InputOutput io) {
		List<CommitMessage> logs = git.log();
		if(logs.isEmpty()) {
			io.writeLine("There is no commits");
		} else {
			io.writeLine(logs);
		}
	}
	
	private static void branches(InputOutput io) {
		List<String> branches = git.branches();
		if(branches.isEmpty()) {
			io.writeLine("No branches");
		} else {
			io.writeLine(branches);
		}
		
	}
	
	private static void commitContent(InputOutput io) {
		String commitName = io.readString("Commit name");
		List<Path> content = git.commitContent(commitName);
		if(content == null) {
			io.writeLine("There is no files");
		} else {
			io.writeLine(content);
		}
	}
	
	private static void switchTo(InputOutput io) {
		String name = io.readString("Switc to: ");
		io.writeLine(git.switchTo(name));
	}
	
	private static void getHead(InputOutput io) {
		String head = git.getHead();
		if(head == null) {
			io.writeLine("There is no head");
		}else {
			io.writeLine(git.getHead());
		}
	}

}
