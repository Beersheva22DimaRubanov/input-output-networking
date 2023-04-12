package telran.git;

import telran.view.InputOutput;	
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class GitAppl {
	public static void main(String[] args) {
		InputOutput io = new StandartInputOutput();
		GitRepositoryImpl gitImpl = GitRepositoryImpl.init();
		Menu menu = (Menu) GitController.getGitItems(gitImpl);
		menu.perform(io);
	}
	
	
}
