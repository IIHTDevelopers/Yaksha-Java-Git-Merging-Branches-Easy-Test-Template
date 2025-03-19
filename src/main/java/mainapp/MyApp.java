package mainapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApp {

    // Method to check if the .git directory exists
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

    public static String checkCommitWithSpecificMessage() {
        String logs;
        try {
            logs = executeCommand("git log --oneline").trim();
            if (!logs.isEmpty()) {

                // Define patterns for "my name" and "my number"
                Pattern namePattern = Pattern.compile(".*my name.*", Pattern.CASE_INSENSITIVE);
                Pattern numberPattern = Pattern.compile(".*my number.*", Pattern.CASE_INSENSITIVE);

                // Matchers for both patterns
                Matcher nameMatcher = namePattern.matcher(logs);
                Matcher numberMatcher = numberPattern.matcher(logs);

                // Check if both patterns are found
                if (nameMatcher.find() && numberMatcher.find()) {
                    return "true"; // Both commits exist
                } else {
                    return "false"; // At least one of the commits is missing
                }
            } else {
                return "false"; // No commits in the repository
            }
        } catch (Exception e) {
            return ""; // Handle exception
        }
    }

    // Method to check if a branch called feature_branch exists
    public static String featureBranchExists() {
        String featureBranchExists;
        try {
            featureBranchExists = executeCommand("git branch --list feature_branch");
            if (featureBranchExists.contains("feature_branch")) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String checkMergeFeatureBranchWithPattern() {
        String reflog;
        try {
            // Execute git reflog command to fetch logs
            reflog = executeCommand("git reflog").trim();
            if (!reflog.isEmpty()) {
                // Define the regex pattern for "merge feature_branch"
                Pattern pattern = Pattern.compile(".*merge feature_branch.*", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(reflog);

                // Check if the pattern matches any line in the reflog
                if (matcher.find()) {
                    return "true"; // Found "merge feature_branch" in the reflog
                } else {
                    return "false"; // No such merge found
                }
            } else {
                return "false"; // Reflog is empty
            }
        } catch (Exception e) {
            return ""; // Handle exception
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

            // Check for at least one commit
            String checkCommitWithSpecificMessage = checkCommitWithSpecificMessage();
            if (checkCommitWithSpecificMessage.equals("true")) {
                System.out.println("Commits found");
            } else {
                System.out.println("No commits found");
                return;
            }

            // Check if feature_branch exists
            String featureBranchExists = featureBranchExists();
            if (featureBranchExists.equals("true")) {
                System.out.println("feature_branch exists.");
            } else {
                System.out.println("feature_branch does not exist.");
            }

            // Check if merge was successful
            String checkMergeFeatureBranchWithPattern = checkMergeFeatureBranchWithPattern();
            if (checkMergeFeatureBranchWithPattern.equals("true")) {
                System.out.println("Merge operation was successful.");
            } else {
                System.out.println("Merge operation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String executeCommand(String command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
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
