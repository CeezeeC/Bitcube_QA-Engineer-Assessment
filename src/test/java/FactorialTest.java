import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FactorialTest {

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
    static void  tearDown() {

        browser.close();
        playwright.close();
    }
}
