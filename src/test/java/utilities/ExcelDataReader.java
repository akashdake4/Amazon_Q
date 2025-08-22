package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader {
    
    public static Object[][] getTestData() {
        String filePath = System.getProperty("user.dir") + "\\testData\\RegistrationTestData.xlsx";
        
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            
            XSSFSheet sheet = workbook.getSheet("TestData");
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();
            
            Object[][] data = new Object[rows][cols];
            
            for (int i = 1; i <= rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i-1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
            return data;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data: " + e.getMessage());
        }
    }
}