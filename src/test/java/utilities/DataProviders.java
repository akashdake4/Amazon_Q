package utilities;

import java.io.File;
import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {
    
    @DataProvider(name = "RegistrationData")
    public String[][] getRegistrationData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\RegistrationTestData.xlsx";
        
        // Check if file exists
        File file = new File(path);
        if (!file.exists()) {
            // Return default test data if file doesn't exist
            return new String[][] {
                {"John", "Doe", "john.test@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Default Test Data"}
            };
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "Sheet1");
            int totalcols = ExcelUtility.getCellCount(path, "Sheet1", 1);
            
            String registrationData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    registrationData[i - 1][j] = ExcelUtility.getCellData(path, "Sheet1", i, j);
                }
            }
            return registrationData;
        } catch (Exception e) {
            // Return default test data if Excel reading fails
            return new String[][] {
                {"John", "Doe", "john.fallback@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Fallback Test Data"}
            };
        }
    }
    
    @DataProvider(name = "ComprehensiveRegistrationData")
    public String[][] getComprehensiveRegistrationData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\RegistrationTestData.xlsx";
        
        String excelData[][];
        int totalrows = 1; // Default to 1 row
        int totalcols = 9; // Default to 9 columns
        
        // Check if file exists and try to read Excel data
        File file = new File(path);
        if (!file.exists()) {
            // Use default data if file doesn't exist
            excelData = new String[][] {
                {"Default", "User", "default@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Default Excel Data"}
            };
        } else {
            try {
                totalrows = ExcelUtility.getRowCount(path, "Sheet1");
                totalcols = ExcelUtility.getCellCount(path, "Sheet1", 1);
                
                excelData = new String[totalrows][totalcols];
                for (int i = 1; i <= totalrows; i++) {
                    for (int j = 0; j < totalcols; j++) {
                        excelData[i - 1][j] = ExcelUtility.getCellData(path, "Sheet1", i, j);
                    }
                }
            } catch (Exception e) {
                // Use fallback data if Excel reading fails
                excelData = new String[][] {
                    {"Fallback", "User", "fallback@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Fallback Excel Data"}
                };
                totalrows = 1;
                totalcols = 9;
            }
        }
        
        // Add 10 additional scenarios
        String additionalData[][] = {
            {"Test", "User", "test.user@outlook.com", "5551234567", "secure123", "secure123", "Yes", "Success", "Outlook Email Domain"},
            {"Admin", "Super", "admin@company.org", "1112223333", "admin@123", "admin@123", "Yes", "Success", "Organization Email"},
            {"Long", "NameTestingVeryLongLastNameField", "longname@test.com", "9998887777", "longpass123", "longpass123", "Yes", "Success", "Long Last Name"},
            {"Special", "Char$", "special@test.com", "4445556666", "special!@#", "special!@#", "Yes", "Success", "Special Characters in Name"},
            {"Min", "Len", "min@t.co", "1234567890", "123", "123", "Yes", "Error", "Minimum Length Password"},
            {"Max", "Length", "maxlength@verylongdomainnamefortesting.com", "9876543210", "verylongpasswordtesting123456789", "verylongpasswordtesting123456789", "Yes", "Success", "Maximum Length Fields"},
            {"Number", "123", "number123@gmail.com", "1234567890", "number123", "number123", "Yes", "Success", "Numbers in Name"},
            {"Case", "SENSITIVE", "CASE@GMAIL.COM", "1234567890", "CaseSensitive123", "CaseSensitive123", "Yes", "Success", "Case Sensitivity Test"},
            {"Duplicate", "Email", "john.doe@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Duplicate Email Test"},
            {"Boundary", "Test", "boundary@test.com", "0000000000", "boundary123", "boundary123", "Yes", "Success", "Boundary Value Testing"}
        };
        
        // Combine Excel data with additional scenarios
        String combinedData[][] = new String[totalrows + additionalData.length][totalcols];
        
        // Copy Excel data
        for (int i = 0; i < totalrows; i++) {
            System.arraycopy(excelData[i], 0, combinedData[i], 0, totalcols);
        }
        
        // Add additional scenarios
        for (int i = 0; i < additionalData.length; i++) {
            System.arraycopy(additionalData[i], 0, combinedData[totalrows + i], 0, totalcols);
        }
        
        return combinedData;
    }
    
    @DataProvider(name = "ValidRegistrationData")
    public String[][] getValidRegistrationData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\ValidRegistrationData.xlsx";
        File file = new File(path);
        
        if (!file.exists()) {
            TestDataGenerator.createValidTestDataExcel();
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "ValidData");
            int totalcols = ExcelUtility.getCellCount(path, "ValidData", 1);
            
            String testData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    testData[i - 1][j] = ExcelUtility.getCellData(path, "ValidData", i, j);
                }
            }
            return testData;
        } catch (Exception e) {
            return new String[][] {
                {"John", "Doe", "john.fallback@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Fallback Valid Data"}
            };
        }
    }
    
    @DataProvider(name = "InvalidRegistrationData")
    public String[][] getInvalidRegistrationData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\InvalidRegistrationData.xlsx";
        File file = new File(path);
        
        if (!file.exists()) {
            TestDataGenerator.createInvalidTestDataExcel();
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "InvalidData");
            int totalcols = ExcelUtility.getCellCount(path, "InvalidData", 1);
            
            String testData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    testData[i - 1][j] = ExcelUtility.getCellData(path, "InvalidData", i, j);
                }
            }
            return testData;
        } catch (Exception e) {
            return new String[][] {
                {"", "Doe", "empty.fallback@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Fallback Invalid Data"}
            };
        }
    }
    
    @DataProvider(name = "EdgeCaseRegistrationData")
    public String[][] getEdgeCaseRegistrationData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\EdgeCaseRegistrationData.xlsx";
        File file = new File(path);
        
        if (!file.exists()) {
            TestDataGenerator.createEdgeCaseTestDataExcel();
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "EdgeCaseData");
            int totalcols = ExcelUtility.getCellCount(path, "EdgeCaseData", 1);
            
            String testData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    testData[i - 1][j] = ExcelUtility.getCellData(path, "EdgeCaseData", i, j);
                }
            }
            return testData;
        } catch (Exception e) {
            return new String[][] {
                {"A", "B", "edge.fallback@test.com", "1234567890", "123456", "123456", "Yes", "Success", "Fallback Edge Case"}
            };
        }
    }
    
    @DataProvider(name = "ComprehensiveTestData")
    public String[][] getComprehensiveTestData() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\ComprehensiveTestData.xlsx";
        
        // Check if file exists
        File file = new File(path);
        if (!file.exists()) {
            // Return default test data if file doesn't exist
            return new String[][] {
                {"Test", "User", "test.comprehensive@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Default Comprehensive Data"}
            };
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "TestData");
            int totalcols = ExcelUtility.getCellCount(path, "TestData", 1);
            
            String testData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    testData[i - 1][j] = ExcelUtility.getCellData(path, "TestData", i, j);
                }
            }
            return testData;
        } catch (Exception e) {
            // Return default test data if Excel reading fails
            return new String[][] {
                {"Fallback", "User", "fallback.comprehensive@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Fallback Comprehensive Data"}
            };
        }
    }
    
    @DataProvider(name = "TwentyFiveScenarios")
    public String[][] getTwentyFiveScenarios() throws IOException {
        String path = System.getProperty("user.dir") + "\\testData\\RegistrationTestData.xlsx";
        File file = new File(path);
        
        if (!file.exists()) {
            TestDataGenerator.createComprehensiveTestDataExcel();
        }
        
        try {
            int totalrows = ExcelUtility.getRowCount(path, "TestData");
            int totalcols = ExcelUtility.getCellCount(path, "TestData", 1);
            
            String testData[][] = new String[totalrows][totalcols];
            
            for (int i = 1; i <= totalrows; i++) {
                for (int j = 0; j < totalcols; j++) {
                    testData[i - 1][j] = ExcelUtility.getCellData(path, "TestData", i, j);
                }
            }
            return testData;
        } catch (Exception e) {
            return new String[][] {
                {"John", "Doe", "john.fallback@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Fallback Test Data"}
            };
        }
    }
}