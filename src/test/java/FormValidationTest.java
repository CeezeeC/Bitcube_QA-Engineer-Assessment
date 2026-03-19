import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FormValidationTest {

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
    void  formValidationStylingTest(){

        page.getByPlaceholder("Enter an integer").fill("M");
        page.locator("#getFactorial").click();
        page.waitForTimeout(3000);

        assertThat(page.locator("#resultDiv")).containsText("Please enter an integer");
        assertThat(page.locator("id=number")).hasCSS("border-color", "rgb(255, 0, 0)");
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
