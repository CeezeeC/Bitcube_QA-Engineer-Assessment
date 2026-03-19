
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UITest {

    static Playwright playwright;
    Page page;
    BrowserType type;
    static Browser browser;
    BrowserContext context;
    @BeforeAll
    static void  setUp(){

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new  BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

    }

    @BeforeEach
    void createPage(){
        context = browser.newContext();
        page  = context.newPage();
        page.navigate(" http://qainterview.pythonanywhere.com");

    }

    @Test
    void testHeading(){
        assertThat(page.getByText("The greatest factorial calculator!")).isVisible();
    }
    @Test
      void  testInput(){
        assertThat(page.getByPlaceholder("Enter an integer")).isVisible();
    }
    @Test
    void calculateButtonVisible(){

        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Calculate!"))).isVisible();
    }

    @Test
    void calculateInput_0(){

        page.getByPlaceholder("Enter an integer").fill("0");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("The factorial of 0 is: 1");
    }
    @Test
    void calculateInput_5(){

        page.getByPlaceholder("Enter an integer").fill("5");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("The factorial of 5 is: 120");
    }
    @Test
    void calculateInput_117(){

        page.getByPlaceholder("Enter an integer").fill("117");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("The factorial of 117 is: 3.969937160808721e+192");
    }
    @Test
    void calculateEmptyInput(){

        page.getByPlaceholder("Enter an integer").fill("");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Please enter an integer");
    }
    @Test
    void calculateLetterInput(){

        page.getByPlaceholder("Enter an integer").fill("M");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Please enter an integer");
    }
    @Test
    void calculateDecimalInput(){

        page.getByPlaceholder("Enter an integer").fill("1,5");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Please enter an integer");
    }
    @Test
    void calculateNegativeIntegerInput(){

        page.getByPlaceholder("Enter an integer").fill("-5");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Please enter a non-negative integer");
    }
    @Test
    void verifyVisualNegativeInputBorderColor(){

        page.getByPlaceholder("Enter an integer").fill("-5");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("id=number")).hasCSS("border-color", "rgb(255, 0, 0)");
    }
    @Test
    void calculateLargeNumberInput(){

        page.getByPlaceholder("Enter an integer").fill("500");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Infinity");
    }
    @Test
    void calculateLargeNumber1Input(){

        page.getByPlaceholder("Enter an integer").fill("1000");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("Number too large to calculate factorial");
    }

    @Test
    void verifyTermsAndConditions(){

        page.getByText("Terms and Conditions").click();

        page.waitForTimeout(5000);
        String expectedText = "This is the terms and conditions document. We are not yet ready with it. Stay tuned!";
        assertThat(page.locator("body")).containsText(expectedText);
    }
    @Test
    void verifyPrivacy(){

        page.getByText("Privacy").click();

        page.waitForTimeout(5000);
        String expectedText = "This is the privacy document. We are not yet ready with it. Stay tuned!";
        assertThat(page.locator("body")).containsText(expectedText);
    }
    @Test
    void verifyAbout(){

        page.getByText("About").click();

        assertThat(page.getByText("About the QA interview application")).isVisible();
        page.waitForTimeout(5000);


    }
    @Test
    void verifyHome(){

        page.getByText("About").click();
        page.waitForTimeout(3000);

        page.getByText("Home").click();
        page.waitForTimeout(3000);
        assertThat(page.getByText("The greatest factorial calculator!")).isVisible();
    }



    @Test
    void  formValidationStylingTest(){

        page.getByPlaceholder("Enter an integer").fill("M");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);

        assertThat(page.locator("#resultDiv")).containsText("Please enter a non-negative integer");
        assertThat(page.locator("id=number")).hasCSS("border-color", "rgb(255, 0, 0)");
    }
    @Test
    void verifyFactialOf12(){

        page.getByPlaceholder("Enter an integer").fill("12");
        page.locator("#getFactorial").click();
        page.waitForTimeout(5000);
        assertThat(page.locator("#resultDiv")).containsText("The factorial of 12 is: 479001600");
    }




    @AfterEach
    void closePage(){

        context.close();
    }
    @AfterAll
    static void  tearDown(){

        browser.close();
        playwright.close();
    }



}
