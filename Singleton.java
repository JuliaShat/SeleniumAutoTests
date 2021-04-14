import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class Singleton {
    public static WebDriver wd = null;
    public static String browser = "chrome";

    public static void start() {
        if (wd == null) {
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "C:/Users/user/selenium jars and drivers/drivers/chromedriver/chromedriver.exe");
                wd = new ChromeDriver();
            }

            wd.manage().window().maximize();
            wd.get(Consts.URL);
        }}


        public static void quit() {
            wd.quit();
            wd = null;
        }

}
