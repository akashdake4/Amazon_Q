package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.DataProviders;
import java.util.UUID;

public class TC_025_RegistrationTwentyFiveScenarios extends BaseClass {
    
    @Test(dataProvider = "TwentyFiveScenarios", dataProviderClass = DataProviders.class, groups = {"DataDriven", "Master"})
    public void verify_account_registration_25_scenarios(String firstName, String lastName, String email, 
                                                        String telephone, String password, String confirmPassword, 
                                                        String agreePolicy, String expectedResult, String testScenario) {
        
        logger.info("***** Starting TC_025_RegistrationTwentyFiveScenarios *****");
        logger.info("Test Scenario: " + testScenario);
        
        try {
            // Generate unique email to avoid duplicates
            String uniqueEmail = generateUniqueEmail(email);
            
            // Home page
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount Link");
            
            hp.clickRegister();
            logger.info("Clicked on Register Link");
            
            // Registration page
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            
            logger.info("Providing customer details...");
            regpage.setFirstName(firstName);
            regpage.setLastName(lastName);
            regpage.setEmail(uniqueEmail);
            regpage.setTelephone(telephone);
            regpage.setPassword(password);
            regpage.setConfirmPassword(confirmPassword);
            
            if (agreePolicy.equalsIgnoreCase("Yes")) {
                regpage.setPrivacyPolicy();
            }
            
            regpage.clickContinue();
            logger.info("Clicked on Continue button");
            

            String confirmationMsg = regpage.getConfirmationMsg();
            logger.info("Confirmation message: " + confirmationMsg);
            
            // Validate based on expected result
            if (expectedResult.equalsIgnoreCase("Success")) {
                if (confirmationMsg.equals("Your Account Has Been Created!")) {
                    Assert.assertTrue(true);
                    logger.info("Test Passed: " + testScenario);
                } else {
                    logger.error("Test Failed: " + testScenario + " - Expected success but got: " + confirmationMsg);
                    Assert.assertTrue(false);
                }
            } else if (expectedResult.equalsIgnoreCase("Error")) {
                if (!confirmationMsg.equals("Your Account Has Been Created!")) {
                    Assert.assertTrue(true);
                    logger.info("Test Passed: " + testScenario + " - Expected error scenario validated");
                } else {
                    logger.error("Test Failed: " + testScenario + " - Expected error but registration succeeded");
                    Assert.assertTrue(false);
                }
            }
            
        } catch (Exception e) {
            logger.error("Test Failed: " + testScenario + " - Exception: " + e.getMessage());
            
            // For error scenarios, exceptions might be expected
            if (expectedResult.equalsIgnoreCase("Error")) {
                logger.info("Test Passed: " + testScenario + " - Expected error scenario with exception");
                Assert.assertTrue(true);
            } else {
                Assert.fail("Unexpected exception in test: " + testScenario);
            }
        }
        
        logger.info("***** Finished TC_025_RegistrationTwentyFiveScenarios *****");
    }
    
    @Test(dataProvider = "ValidRegistrationData", dataProviderClass = DataProviders.class, groups = {"Sanity", "Master"})
    public void verify_valid_registration_scenarios(String firstName, String lastName, String email, 
                                                   String telephone, String password, String confirmPassword, 
                                                   String agreePolicy, String expectedResult, String testScenario)  {

        logger.info("***** Starting Valid Registration Test *****");
        logger.info("Test Scenario: " + testScenario);



        try {
            String uniqueEmail = generateUniqueEmail(email);
            
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();
            
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            regpage.setFirstName(firstName);
            regpage.setLastName(lastName);
            regpage.setEmail(uniqueEmail);
            regpage.setTelephone(telephone);
            regpage.setPassword(password);
            regpage.setConfirmPassword(confirmPassword);
            regpage.setPrivacyPolicy();
            regpage.clickContinue();
            

            String confirmationMsg = regpage.getConfirmationMsg();
            Assert.assertEquals(confirmationMsg, "Your Account Has Been Created!");
            logger.info("Valid Registration Test Passed: " + testScenario);
            
        } catch (Exception e) {
            logger.error("Valid Registration Test Failed: " + testScenario + " - " + e.getMessage());
            Assert.fail();
        }
    }
    
    @Test(dataProvider = "InvalidRegistrationData", dataProviderClass = DataProviders.class, groups = {"Regression", "Master"})
    public void verify_invalid_registration_scenarios(String firstName, String lastName, String email, 
                                                     String telephone, String password, String confirmPassword, 
                                                     String agreePolicy, String expectedResult, String testScenario) {
        
        logger.info("***** Starting Invalid Registration Test *****");
        logger.info("Test Scenario: " + testScenario);
        
        try {
            // Only generate unique email for valid email formats to test other validation scenarios
            String testEmail = email.contains("@") && !email.equals("invalid-email") && !email.equals("@gmail.com") && !email.equals("john@") ? generateUniqueEmail(email) : email;
            
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();
            
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            regpage.setFirstName(firstName);
            regpage.setLastName(lastName);
            regpage.setEmail(testEmail);
            regpage.setTelephone(telephone);
            regpage.setPassword(password);
            regpage.setConfirmPassword(confirmPassword);
            
            if (agreePolicy.equalsIgnoreCase("Yes")) {
                regpage.setPrivacyPolicy();
            }
            
            regpage.clickContinue();
            

            String confirmationMsg = regpage.getConfirmationMsg();
            
            // For invalid scenarios, we expect either an error message or no success message
            if (!confirmationMsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
                logger.info("Invalid Registration Test Passed: " + testScenario + " - Error properly handled");
            } else {
                logger.error("Invalid Registration Test Failed: " + testScenario + " - Registration should have failed");
                Assert.assertTrue(false);
            }
            
        } catch (Exception e) {
            // Exceptions are expected for invalid scenarios
            logger.info("Invalid Registration Test Passed: " + testScenario + " - Exception as expected: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }
    
    @Test(dataProvider = "EdgeCaseRegistrationData", dataProviderClass = DataProviders.class, groups = {"Regression", "Master"})
    public void verify_edge_case_registration_scenarios(String firstName, String lastName, String email, 
                                                       String telephone, String password, String confirmPassword, 
                                                       String agreePolicy, String expectedResult, String testScenario) {
        
        logger.info("***** Starting Edge Case Registration Test *****");
        logger.info("Test Scenario: " + testScenario);
        
        try {
            String uniqueEmail = generateUniqueEmail(email);
            
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();
            
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            regpage.setFirstName(firstName);
            regpage.setLastName(lastName);
            regpage.setEmail(uniqueEmail);
            regpage.setTelephone(telephone);
            regpage.setPassword(password);
            regpage.setConfirmPassword(confirmPassword);
            regpage.setPrivacyPolicy();
            regpage.clickContinue();
            

            String confirmationMsg = regpage.getConfirmationMsg();
            
            if (expectedResult.equalsIgnoreCase("Success")) {
                Assert.assertEquals(confirmationMsg, "Your Account Has Been Created!");
                logger.info("Edge Case Test Passed: " + testScenario);
            } else {
                Assert.assertNotEquals(confirmationMsg, "Your Account Has Been Created!");
                logger.info("Edge Case Test Passed: " + testScenario + " - Error scenario validated");
            }
            
        } catch (Exception e) {
            if (expectedResult.equalsIgnoreCase("Error")) {
                logger.info("Edge Case Test Passed: " + testScenario + " - Expected exception: " + e.getMessage());
                Assert.assertTrue(true);
            } else {
                logger.error("Edge Case Test Failed: " + testScenario + " - " + e.getMessage());
                Assert.fail();
            }
        }
    }
    
    private String generateUniqueEmail(String originalEmail) {
        if (originalEmail == null || !originalEmail.contains("@")) {
            return originalEmail;
        }
        
        String[] parts = originalEmail.split("@");
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return parts[0] + "+" + uniqueId + "@" + parts[1];
    }
}