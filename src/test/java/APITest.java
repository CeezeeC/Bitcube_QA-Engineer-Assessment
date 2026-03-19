import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class APITest {


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
    void verifyApiCallHasCorrectHeadersAndParameters() {

        // Capture the factorial API request
        AtomicReference<Request> apiRequest = new AtomicReference<>();
        page.onRequest(request -> {
            if (request.url().contains("/factorial")) {
                apiRequest.set(request);
            }
        });

        // Enter number and click calculate
        page.getByPlaceholder("Enter an integer").fill("12");
        page.locator("#getFactorial").click();

        page.waitForTimeout(2000);

        Request request = apiRequest.get();
        assertNotNull(request, "API request was not made");

        String requestUrl = request.url();
        System.out.println("API URL: " + requestUrl);

        String postData = request.postData();
        assertNotNull(postData, "Expected request to have POST data");
        assertTrue(postData.contains("12"), "Expected request body to contain '12', but got: " + postData);
        System.out.println("POST Data: " + postData);

        Map<String, String> headers = request.headers();
        assertTrue(headers.containsKey("accept"), "Expected 'accept' header to be present");
        assertTrue(headers.get("accept").contains("*/*") || headers.get("accept").contains("application/json"),
                "Unexpected Accept header value: " + headers.get("accept"));
        System.out.println("Headers: " + headers);
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

