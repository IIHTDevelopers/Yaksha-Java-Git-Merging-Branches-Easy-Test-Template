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
    public void testCheckCommitWithSpecificMessage() throws IOException {
        try {
            String output = MyApp.checkCommitWithSpecificMessage();
            System.out.println(output);
            yakshaAssert(currentTest(), output.equals("true"), businessTestFile);
        } catch (Exception ex) {
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }

    @Test
    public void testFeatureBranchExists() throws IOException {
        try {
            String output = MyApp.featureBranchExists();
            System.out.println(output);
            yakshaAssert(currentTest(), output.equals("true"), businessTestFile);
        } catch (Exception ex) {
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }

    @Test
    public void testCheckMergeFeatureBranchWithPattern() throws IOException {
        try {
            String output = MyApp.checkMergeFeatureBranchWithPattern();
            System.out.println(output);
            yakshaAssert(currentTest(), output.equals("true"), businessTestFile);
        } catch (Exception ex) {
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }
}
