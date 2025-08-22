package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestDataGenerator {
    
    public static void createComprehensiveTestDataExcel() {
        String filePath = System.getProperty("user.dir") + "\\testData\\RegistrationTestData.xlsx";
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("TestData");
            
            // Create header row
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FirstName");
            headerRow.createCell(1).setCellValue("LastName");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Telephone");
            headerRow.createCell(4).setCellValue("Password");
            headerRow.createCell(5).setCellValue("ConfirmPassword");
            headerRow.createCell(6).setCellValue("AgreePolicy");
            headerRow.createCell(7).setCellValue("ExpectedResult");
            headerRow.createCell(8).setCellValue("TestScenario");
            
            // Comprehensive test data with extensive negative scenarios
            String[][] testData = {
                // Valid scenarios
                {"John", "Doe", "john.test@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Valid Registration Gmail"},
                {"Jane", "Smith", "jane.test@yahoo.com", "9876543210", "password123", "password123", "Yes", "Success", "Valid Registration Yahoo"},
                {"Mike", "Johnson", "mike@outlook.com", "5551234567", "secure123", "secure123", "Yes", "Success", "Valid Registration Outlook"},
                
                // Empty field scenarios
                {"", "Doe", "empty1@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Empty First Name"},
                {"John", "", "empty2@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Empty Last Name"},
                {"John", "Doe", "", "1234567890", "test123", "test123", "Yes", "Error", "Empty Email"},
                {"John", "Doe", "empty3@gmail.com", "", "test123", "test123", "Yes", "Error", "Empty Phone"},
                {"John", "Doe", "empty4@gmail.com", "1234567890", "", "", "Yes", "Error", "Empty Password"},
                {"John", "Doe", "empty5@gmail.com", "1234567890", "test123", "", "Yes", "Error", "Empty Confirm Password"},
                
                // Invalid email formats
                {"John", "Doe", "invalid-email", "1234567890", "test123", "test123", "Yes", "Error", "Invalid Email Format"},
                {"John", "Doe", "@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Missing Email Username"},
                {"John", "Doe", "john@", "1234567890", "test123", "test123", "Yes", "Error", "Missing Email Domain"},
                {"John", "Doe", "john@@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Double @ in Email"},
                {"John", "Doe", "john@.com", "1234567890", "test123", "test123", "Yes", "Error", "Missing Domain Name"},
                {"John", "Doe", "john@gmail.", "1234567890", "test123", "test123", "Yes", "Error", "Missing TLD"},
                {"John", "Doe", "john@gmail..com", "1234567890", "test123", "test123", "Yes", "Error", "Double Dot in Domain"},
                {"John", "Doe", "john.@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Dot Before @"},
                {"John", "Doe", ".john@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Starting with Dot"},
                {"John", "Doe", "john@gmail,com", "1234567890", "test123", "test123", "Yes", "Error", "Comma Instead of Dot"},
                {"John", "Doe", "john gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Missing @ Symbol"},
                
                // Invalid phone numbers
                {"John", "Doe", "phone1@gmail.com", "123", "test123", "test123", "Yes", "Error", "Too Short Phone"},
                {"John", "Doe", "phone2@gmail.com", "12345678901234567890", "test123", "test123", "Yes", "Error", "Too Long Phone"},
                {"John", "Doe", "phone3@gmail.com", "abcdefghij", "test123", "test123", "Yes", "Error", "Alphabetic Phone"},
                {"John", "Doe", "phone4@gmail.com", "123-456-7890", "test123", "test123", "Yes", "Error", "Phone with Dashes"},
                {"John", "Doe", "phone5@gmail.com", "(123)456-7890", "test123", "test123", "Yes", "Error", "Phone with Brackets"},
                {"John", "Doe", "phone6@gmail.com", "+1234567890", "test123", "test123", "Yes", "Error", "Phone with Plus"},
                {"John", "Doe", "phone7@gmail.com", "123 456 7890", "test123", "test123", "Yes", "Error", "Phone with Spaces"},
                {"John", "Doe", "phone8@gmail.com", "!@#$%^&*()", "test123", "test123", "Yes", "Error", "Special Chars in Phone"},
                
                // Password validation scenarios
                {"John", "Doe", "pass1@gmail.com", "1234567890", "12", "12", "Yes", "Error", "Too Short Password"},
                {"John", "Doe", "pass2@gmail.com", "1234567890", "test123", "different123", "Yes", "Error", "Password Mismatch"},
                {"John", "Doe", "pass3@gmail.com", "1234567890", "   ", "   ", "Yes", "Error", "Whitespace Password"},
                {"John", "Doe", "pass4@gmail.com", "1234567890", "password", "password", "Yes", "Error", "Weak Password"},
                
                // Privacy policy scenarios
                {"John", "Doe", "policy1@gmail.com", "1234567890", "test123", "test123", "No", "Error", "Privacy Policy Not Agreed"},
                
                // Special character scenarios in names
                {"John@", "Doe", "special1@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "@ in First Name"},
                {"John", "Doe#", "special2@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "# in Last Name"},
                {"John$", "Doe%", "special3@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Special Chars in Names"},
                {"<script>", "alert", "script@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Script Injection in Name"},
                {"'; DROP TABLE--", "SQL", "sql@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "SQL Injection in Name"},
                
                // Boundary value testing
                {"A", "B", "min@test.com", "1234567890", "123456", "123456", "Yes", "Success", "Minimum Valid Length"},
                {"VeryLongFirstNameTestingBoundaryValues", "VeryLongLastNameTestingBoundaryValues", "maxlength@verylongdomainnamefortesting.com", "9876543210", "verylongpasswordtesting123456789", "verylongpasswordtesting123456789", "Yes", "Success", "Maximum Length Fields"},
                
                // Unicode and international characters
                {"José", "García", "jose@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Unicode Characters"},
                {"李", "小明", "chinese@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Chinese Characters"},
                {"محمد", "علي", "arabic@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Arabic Characters"},
                {"Владимир", "Петров", "russian@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Cyrillic Characters"},
                
                // Case sensitivity tests
                {"JOHN", "DOE", "UPPERCASE@GMAIL.COM", "1234567890", "TEST123", "TEST123", "Yes", "Success", "All Uppercase"},
                {"john", "doe", "lowercase@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "All Lowercase"},
                {"JoHn", "DoE", "MiXeD@GmAiL.CoM", "1234567890", "TeSt123", "TeSt123", "Yes", "Success", "Mixed Case"},
                
                // Numeric scenarios
                {"123", "456", "numbers@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Numeric Names"},
                {"0", "0", "zero@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Zero Names"},
                
                // Whitespace scenarios
                {" John ", " Doe ", "whitespace@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Names with Leading/Trailing Spaces"},
                {"John", "Doe", " email@gmail.com ", "1234567890", "test123", "test123", "Yes", "Error", "Email with Spaces"},
                {"John", "Doe", "tab@gmail.com", " 1234567890 ", "test123", "test123", "Yes", "Error", "Phone with Spaces"}
            };
            
            // Add test data rows
            for (int i = 0; i < testData.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < testData[i].length; j++) {
                    row.createCell(j).setCellValue(testData[i][j]);
                }
            }
            
            // Auto-size columns
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Comprehensive test data Excel file created successfully at: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
        }
    }
    
    public static void createValidTestDataExcel() {
        String filePath = System.getProperty("user.dir") + "\\testData\\ValidRegistrationData.xlsx";
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("ValidData");
            
            // Create header row
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FirstName");
            headerRow.createCell(1).setCellValue("LastName");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Telephone");
            headerRow.createCell(4).setCellValue("Password");
            headerRow.createCell(5).setCellValue("ConfirmPassword");
            headerRow.createCell(6).setCellValue("AgreePolicy");
            headerRow.createCell(7).setCellValue("ExpectedResult");
            headerRow.createCell(8).setCellValue("TestScenario");
            
            String[][] validData = {
                {"John", "Doe", "john.valid@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Valid Gmail Registration"},
                {"Jane", "Smith", "jane.valid@yahoo.com", "9876543210", "password123", "password123", "Yes", "Success", "Valid Yahoo Registration"},
                {"Mike", "Johnson", "mike.valid@outlook.com", "5551234567", "secure123", "secure123", "Yes", "Success", "Valid Outlook Registration"},
                {"Sarah", "Wilson", "sarah.valid@company.org", "7778889999", "corporate123", "corporate123", "Yes", "Success", "Valid Corporate Email"},
                {"David", "Brown", "david.valid@hotmail.com", "1112223333", "strong123", "strong123", "Yes", "Success", "Valid Hotmail Registration"},
                {"Lisa", "Davis", "lisa.valid@test.edu", "4445556666", "education123", "education123", "Yes", "Success", "Valid Educational Email"},
                {"Tom", "Miller", "tom.valid@domain.net", "7890123456", "network123", "network123", "Yes", "Success", "Valid .net Domain"},
                {"Anna", "Garcia", "anna.valid@example.info", "3216549870", "info123", "info123", "Yes", "Success", "Valid .info Domain"},
                {"Chris", "Martinez", "chris.valid@site.biz", "6547891230", "business123", "business123", "Yes", "Success", "Valid .biz Domain"},
                {"Emma", "Rodriguez", "emma.valid@web.co", "9871234560", "website123", "website123", "Yes", "Success", "Valid .co Domain"}
            };
            
            for (int i = 0; i < validData.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < validData[i].length; j++) {
                    row.createCell(j).setCellValue(validData[i][j]);
                }
            }
            
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Valid test data Excel file created at: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error creating valid test data Excel: " + e.getMessage());
        }
    }
    
    public static void createInvalidTestDataExcel() {
        String filePath = System.getProperty("user.dir") + "\\testData\\InvalidRegistrationData.xlsx";
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("InvalidData");
            
            // Create header row
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FirstName");
            headerRow.createCell(1).setCellValue("LastName");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Telephone");
            headerRow.createCell(4).setCellValue("Password");
            headerRow.createCell(5).setCellValue("ConfirmPassword");
            headerRow.createCell(6).setCellValue("AgreePolicy");
            headerRow.createCell(7).setCellValue("ExpectedResult");
            headerRow.createCell(8).setCellValue("TestScenario");
            
            String[][] invalidData = {
                // Empty field scenarios
                {"", "Doe", "empty1@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Empty First Name"},
                {"John", "", "empty2@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Empty Last Name"},
                {"John", "Doe", "", "1234567890", "test123", "test123", "Yes", "Error", "Empty Email"},
                {"John", "Doe", "empty3@gmail.com", "", "test123", "test123", "Yes", "Error", "Empty Phone"},
                {"John", "Doe", "empty4@gmail.com", "1234567890", "", "", "Yes", "Error", "Empty Password"},
                {"John", "Doe", "empty5@gmail.com", "1234567890", "test123", "", "Yes", "Error", "Empty Confirm Password"},
                
                // Invalid email formats - comprehensive list
                {"John", "Doe", "invalid-email", "1234567890", "test123", "test123", "Yes", "Error", "No @ Symbol"},
                {"John", "Doe", "@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Missing Username"},
                {"John", "Doe", "john@", "1234567890", "test123", "test123", "Yes", "Error", "Missing Domain"},
                {"John", "Doe", "john@@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Double @ Symbol"},
                {"John", "Doe", "john@.com", "1234567890", "test123", "test123", "Yes", "Error", "Missing Domain Name"},
                {"John", "Doe", "john@gmail.", "1234567890", "test123", "test123", "Yes", "Error", "Missing TLD"},
                {"John", "Doe", "john@gmail..com", "1234567890", "test123", "test123", "Yes", "Error", "Double Dot in Domain"},
                {"John", "Doe", "john.@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Dot Before @"},
                {"John", "Doe", ".john@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Starting with Dot"},
                {"John", "Doe", "jo..hn@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Consecutive Dots"},
                {"John", "Doe", "john@gmail,com", "1234567890", "test123", "test123", "Yes", "Error", "Comma Instead of Dot"},
                {"John", "Doe", "john gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Space Instead of @"},
                {"John", "Doe", "john@gmail .com", "1234567890", "test123", "test123", "Yes", "Error", "Space in Domain"},
                {"John", "Doe", "john@", "1234567890", "test123", "test123", "Yes", "Error", "Only @ Symbol"},
                {"John", "Doe", "john@-gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Hyphen at Start of Domain"},
                {"John", "Doe", "john@gmail-.com", "1234567890", "test123", "test123", "Yes", "Error", "Hyphen at End of Domain"},
                {"John", "Doe", "john@gmail.c", "1234567890", "test123", "test123", "Yes", "Error", "Single Char TLD"},
                {"John", "Doe", "john@gmail.toolongTLD", "1234567890", "test123", "test123", "Yes", "Error", "Too Long TLD"},
                
                // Invalid phone numbers - comprehensive list
                {"John", "Doe", "phone1@gmail.com", "123", "test123", "test123", "Yes", "Error", "Too Short Phone"},
                {"John", "Doe", "phone2@gmail.com", "12345678901234567890", "test123", "test123", "Yes", "Error", "Too Long Phone"},
                {"John", "Doe", "phone3@gmail.com", "abcdefghij", "test123", "test123", "Yes", "Error", "Alphabetic Phone"},
                {"John", "Doe", "phone4@gmail.com", "123-456-7890", "test123", "test123", "Yes", "Error", "Phone with Dashes"},
                {"John", "Doe", "phone5@gmail.com", "(123)456-7890", "test123", "test123", "Yes", "Error", "Phone with Brackets"},
                {"John", "Doe", "phone6@gmail.com", "+1234567890", "test123", "test123", "Yes", "Error", "Phone with Plus"},
                {"John", "Doe", "phone7@gmail.com", "123 456 7890", "test123", "test123", "Yes", "Error", "Phone with Spaces"},
                {"John", "Doe", "phone8@gmail.com", "!@#$%^&*()", "test123", "test123", "Yes", "Error", "Special Chars in Phone"},
                {"John", "Doe", "phone9@gmail.com", "123.456.7890", "test123", "test123", "Yes", "Error", "Phone with Dots"},
                {"John", "Doe", "phone10@gmail.com", "123,456,7890", "test123", "test123", "Yes", "Error", "Phone with Commas"},
                {"John", "Doe", "phone11@gmail.com", "0000000000", "test123", "test123", "Yes", "Error", "All Zeros Phone"},
                {"John", "Doe", "phone12@gmail.com", "1111111111", "test123", "test123", "Yes", "Error", "Repeated Digits Phone"},
                
                // Password validation scenarios
                {"John", "Doe", "pass1@gmail.com", "1234567890", "1", "1", "Yes", "Error", "Single Char Password"},
                {"John", "Doe", "pass2@gmail.com", "1234567890", "12", "12", "Yes", "Error", "Two Char Password"},
                {"John", "Doe", "pass3@gmail.com", "1234567890", "test123", "different123", "Yes", "Error", "Password Mismatch"},
                {"John", "Doe", "pass4@gmail.com", "1234567890", "   ", "   ", "Yes", "Error", "Whitespace Password"},
                {"John", "Doe", "pass5@gmail.com", "1234567890", "password", "password", "Yes", "Error", "Common Weak Password"},
                {"John", "Doe", "pass6@gmail.com", "1234567890", "123456", "123456", "Yes", "Error", "Numeric Only Password"},
                {"John", "Doe", "pass7@gmail.com", "1234567890", "abcdef", "abcdef", "Yes", "Error", "Alphabetic Only Password"},
                {"John", "Doe", "pass8@gmail.com", "1234567890", "test123", "TEST123", "Yes", "Error", "Case Mismatch Password"},
                
                // Privacy policy scenarios
                {"John", "Doe", "policy1@gmail.com", "1234567890", "test123", "test123", "No", "Error", "Privacy Policy Not Agreed"},
                {"Jane", "Smith", "policy2@gmail.com", "9876543210", "secure123", "secure123", "No", "Error", "Privacy Policy Declined"},
                
                // Special characters in names
                {"John@", "Doe", "special1@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "@ in First Name"},
                {"John", "Doe#", "special2@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "# in Last Name"},
                {"John$", "Doe%", "special3@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Multiple Special Chars"},
                {"John&", "Doe*", "special4@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "& and * in Names"},
                {"John(", "Doe)", "special5@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Brackets in Names"},
                {"John[", "Doe]", "special6@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Square Brackets in Names"},
                {"John{", "Doe}", "special7@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Curly Brackets in Names"},
                {"John|", "Doe\\", "special8@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Pipe and Backslash"},
                {"John;", "Doe:", "special9@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Semicolon and Colon"},
                {"John'", "Doe\"", "special10@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Quotes in Names"},
                
                // Security test scenarios
                {"<script>", "alert", "script@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Script Tag in Name"},
                {"'; DROP TABLE--", "SQL", "sql@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "SQL Injection Attempt"},
                {"../../../etc/passwd", "Path", "path@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Path Traversal Attempt"},
                {"<img src=x onerror=alert(1)>", "XSS", "xss@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "XSS Attempt"},
                
                // Whitespace scenarios
                {" John ", " Doe ", "whitespace1@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Leading/Trailing Spaces in Names"},
                {"John", "Doe", " email@gmail.com ", "1234567890", "test123", "test123", "Yes", "Error", "Spaces Around Email"},
                {"John", "Doe", "tab@gmail.com", " 1234567890 ", "test123", "test123", "Yes", "Error", "Spaces Around Phone"},
                {"John", "Doe", "space@gmail.com", "1234567890", " test123 ", " test123 ", "Yes", "Error", "Spaces Around Password"},
                {"\t\n\r", "\t\n\r", "control@gmail.com", "1234567890", "test123", "test123", "Yes", "Error", "Control Characters in Names"}
            };
            
            for (int i = 0; i < invalidData.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < invalidData[i].length; j++) {
                    row.createCell(j).setCellValue(invalidData[i][j]);
                }
            }
            
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Invalid test data Excel file created at: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error creating invalid test data Excel: " + e.getMessage());
        }
    }
    
    public static void createEdgeCaseTestDataExcel() {
        String filePath = System.getProperty("user.dir") + "\\testData\\EdgeCaseRegistrationData.xlsx";
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("EdgeCaseData");
            
            // Create header row
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("FirstName");
            headerRow.createCell(1).setCellValue("LastName");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Telephone");
            headerRow.createCell(4).setCellValue("Password");
            headerRow.createCell(5).setCellValue("ConfirmPassword");
            headerRow.createCell(6).setCellValue("AgreePolicy");
            headerRow.createCell(7).setCellValue("ExpectedResult");
            headerRow.createCell(8).setCellValue("TestScenario");
            
            String[][] edgeCaseData = {
                // Boundary value testing
                {"A", "B", "min@test.com", "1234567890", "123456", "123456", "Yes", "Success", "Minimum Valid Length"},
                {"VeryLongFirstNameTestingBoundaryValuesForMaximumLength", "VeryLongLastNameTestingBoundaryValuesForMaximumLength", "verylongemailaddressfortestingmaximumlengthboundaryvalues@verylongdomainnamefortestingmaximumlengthboundaryvalues.com", "9876543210", "verylongpasswordfortestingmaximumlengthboundaryvalues123456789", "verylongpasswordfortestingmaximumlengthboundaryvalues123456789", "Yes", "Success", "Maximum Length Fields"},
                
                // Unicode and international characters
                {"José", "García", "jose.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Spanish Unicode"},
                {"李", "小明", "chinese.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Chinese Characters"},
                {"محمد", "علي", "arabic.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Arabic Characters"},
                {"Владимир", "Петров", "russian.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Cyrillic Characters"},
                {"Ñoño", "Müller", "special.chars@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Special Latin Characters"},
                {"Åse", "Øyvind", "nordic@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Nordic Characters"},
                
                // Email edge cases
                {"Test", "User", "test+tag@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Email with Plus Sign"},
                {"Test", "User", "test.dot@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Email with Dot"},
                {"Test", "User", "test_underscore@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Email with Underscore"},
                {"Test", "User", "test-hyphen@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Email with Hyphen"},
                {"Test", "User", "123numbers@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "Email Starting with Numbers"},
                {"Test", "User", "a@b.co", "1234567890", "test123", "test123", "Yes", "Success", "Shortest Valid Email"},
                
                // Case sensitivity tests
                {"JOHN", "DOE", "UPPERCASE.EDGE@GMAIL.COM", "1234567890", "TEST123", "TEST123", "Yes", "Success", "All Uppercase"},
                {"john", "doe", "lowercase.edge@gmail.com", "1234567890", "test123", "test123", "Yes", "Success", "All Lowercase"},
                {"JoHn", "DoE", "MiXeD.CaSe@GmAiL.CoM", "1234567890", "TeSt123", "TeSt123", "Yes", "Success", "Mixed Case"},
                
                // Numeric scenarios
                {"123", "456", "numbers.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Numeric Names"},
                {"0", "0", "zero.edge@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Zero Names"},
                {"1", "2", "single.digits@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Single Digit Names"},
                
                // Phone number edge cases
                {"Test", "User", "phone.edge1@test.com", "0000000000", "test123", "test123", "Yes", "Success", "All Zeros Phone"},
                {"Test", "User", "phone.edge2@test.com", "9999999999", "test123", "test123", "Yes", "Success", "All Nines Phone"},
                {"Test", "User", "phone.edge3@test.com", "1000000000", "test123", "test123", "Yes", "Success", "Starting with 1"},
                {"Test", "User", "phone.edge4@test.com", "1234567890", "test123", "test123", "Yes", "Success", "Sequential Numbers"},
                
                // Password edge cases
                {"Test", "User", "pass.edge1@test.com", "1234567890", "123456", "123456", "Yes", "Success", "Minimum Length Password"},
                {"Test", "User", "pass.edge2@test.com", "1234567890", "!@#$%^&*()", "!@#$%^&*()", "Yes", "Success", "Special Characters Password"},
                {"Test", "User", "pass.edge3@test.com", "1234567890", "PASSWORD123", "PASSWORD123", "Yes", "Success", "Uppercase Password"},
                {"Test", "User", "pass.edge4@test.com", "1234567890", "password123", "password123", "Yes", "Success", "Lowercase Password"},
                {"Test", "User", "pass.edge5@test.com", "1234567890", "Pass123!@#", "Pass123!@#", "Yes", "Success", "Mixed Password"},
                
                // Domain edge cases
                {"Test", "User", "domain.edge1@a.co", "1234567890", "test123", "test123", "Yes", "Success", "Single Char Domain"},
                {"Test", "User", "domain.edge2@test.museum", "1234567890", "test123", "test123", "Yes", "Success", "Long TLD"},
                {"Test", "User", "domain.edge3@sub.domain.test.com", "1234567890", "test123", "test123", "Yes", "Success", "Multiple Subdomains"},
                {"Test", "User", "domain.edge4@test-domain.com", "1234567890", "test123", "test123", "Yes", "Success", "Hyphenated Domain"}
            };
            
            for (int i = 0; i < edgeCaseData.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < edgeCaseData[i].length; j++) {
                    row.createCell(j).setCellValue(edgeCaseData[i][j]);
                }
            }
            
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Edge case test data Excel file created at: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error creating edge case test data Excel: " + e.getMessage());
        }
    }
    
    public static void createTestDataExcel() {
        createComprehensiveTestDataExcel();
    }
    
    public static void main(String[] args) {
        createComprehensiveTestDataExcel();
        createValidTestDataExcel();
        createInvalidTestDataExcel();
        createEdgeCaseTestDataExcel();
        System.out.println("All test data Excel files created successfully!");
    }
}