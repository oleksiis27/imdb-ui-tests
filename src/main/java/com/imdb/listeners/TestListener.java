package com.imdb.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        log.info("STARTING: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("PASSED: {} ({}ms)", result.getMethod().getMethodName(), getDuration(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("FAILED: {} - {}", result.getMethod().getMethodName(),
                result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("SKIPPED: {}", result.getMethod().getMethodName());
    }

    private long getDuration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}