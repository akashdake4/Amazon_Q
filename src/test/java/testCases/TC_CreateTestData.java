package testCases;

import org.testng.annotations.Test;
import testBase.BaseClass;
import utilities.TestDataGenerator;

public class TC_CreateTestData extends BaseClass {
    
    @Test(priority = 1, groups = {"Setup"})
    public void createExcelTestData() {
        logger.info("***** Creating Excel Test Data File *****");
        
        try {
            TestDataGenerator.createTestDataExcel();
            logger.info("Excel test data file created successfully");
        } catch (Exception e) {
            logger.error("Failed to create Excel test data file: " + e.getMessage());
        }
        
        logger.info("***** Excel Test Data Creation Completed *****");
    }
}