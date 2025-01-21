package com.libraryapp.test.functional;

import static com.libraryapp.test.utils.TestUtils.businessTestFile;
import static com.libraryapp.test.utils.TestUtils.currentTest;
import static com.libraryapp.test.utils.TestUtils.testReport;
import static com.libraryapp.test.utils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import mainapp.MyApp;

public class MainFunctionalTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testDirectoryExists() throws IOException {
		try {
			String output = MyApp.directoryExists();
			System.out.println(output);
			yakshaAssert(currentTest(), output.equals("true"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testAtleastOneCommit() throws IOException {
		try {
			String output = MyApp.atleastOneCommit();
			System.out.println(output);
			yakshaAssert(currentTest(), output.equals("true"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testRevertLastCommit() throws IOException {
		try {
			// Revert the last commit
			String revertOutput = MyApp.revertLastCommit();
			System.out.println(revertOutput);
			yakshaAssert(currentTest(), !revertOutput.isEmpty(), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGitStatusAfterRevert() throws IOException {
		try {
			// Get the status after revert operation
			String status = MyApp.getStatus();
			System.out.println(status);
			yakshaAssert(currentTest(), status.contains("Changes to be committed"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testCommitAfterRevert() throws IOException {
		try {
			// Simulate a commit after revert to finalize the changes
			String commitOutput = MyApp.executeCommand("git commit -m 'Reverted last commit'");
			System.out.println(commitOutput);
			yakshaAssert(currentTest(), commitOutput.contains("[master] Reverted last commit"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
