package mainapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class MyApp {

	public static String directoryExists() {
		String gitDirectoryExists;
		try {
			gitDirectoryExists = executeCommand("git rev-parse --is-inside-work-tree").trim();
			if (gitDirectoryExists.equals("true")) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String atleastOneCommit() {
		String atleastOneCommit;
		try {
			atleastOneCommit = executeCommand("git log --oneline").trim();
			if (!atleastOneCommit.isEmpty()) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String revertLastCommit() {
		try {
			// Perform the revert operation to undo the last commit
			String revertOutput = executeCommand("git revert --no-commit HEAD");
			return revertOutput.trim();
		} catch (Exception e) {
			return "";
		}
	}

	public static String getStatus() {
		try {
			// Get the status of the repository
			String status = executeCommand("git status").trim();
			return status;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		try {
			// Check if .git directory exists
			String gitDirectoryExists = directoryExists();
			if (gitDirectoryExists.equals("true")) {
				System.out.println("Git repository initialized successfully.");
			} else {
				System.out.println("Git repository not initialized.");
				return;
			}

			// Check for commit (This checks if there's at least one commit)
			String atleastOneCommit = atleastOneCommit();
			if (atleastOneCommit.equals("true")) {
				System.out.println("Changes have been committed.");
			} else {
				System.out.println("No changes committed.");
				return;
			}

			// Revert the last commit
			String revertOutput = revertLastCommit();
			if (!revertOutput.isEmpty()) {
				System.out.println("Successfully reverted the last commit.");
			} else {
				System.out.println("Failed to revert the last commit.");
			}

			// Check the repository status after revert
			String status = getStatus();
			System.out.println("Git status after revert: " + status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String executeCommand(String command) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("utils"));
		processBuilder.command("bash", "-c", command);
		Process process = processBuilder.start();

		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line).append("\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0) {
			return output.toString();
		} else {
			throw new RuntimeException("Failed to execute command: " + command);
		}
	}
}
