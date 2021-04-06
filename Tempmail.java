import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tempmail {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/user/selenium jars and drivers/drivers/chromedriver/chromedriver.exe");
        WebDriver wd = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(wd, 10);
        wd.get("https://tempmail.plus/ru/"); //1

       wd.findElement(By.cssSelector("[data-tr=\"new_random_name\"]")).click(); //2


       wd.findElement(By.id("domain")).click();
       wd.findElement(By.xpath("/html/body/div[8]/div[1]/div[2]/div[1]/form/div/div[2]/div/button[6]")).click(); //3

        String part1 = wd.findElement(By.cssSelector("#pre_button")).getAttribute("value");
        String part2 = wd.findElement(By.cssSelector("#domain")).getAttribute("textContent");
        String email = part1 + part2;//4

        wd.findElement(By.id("pre_settings")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/form/div[3]/div[1]/div[2]/label[1]"))).click();
       // wd.findElement(By.xpath("//[@id=\"modal-settings\"]/div/form/div[3]/div[2]/input")).click();  //5

     //  wd.findElement(By.id("pre_settings")).click();
        WebElement time = wd.findElement(By.xpath("//*[@id=\"modal-settings\"]/div/form/div[3]/div[2]/input"));
        if (time.isSelected())
        {
            System.out.println("10min is checked");
        }
        else
        {
            System.out.println("10min is not checked");
        }
        String secretAddress = wd.findElement(By.cssSelector("#secret-address")).getAttribute("textContent");
        wd.findElement(By.cssSelector("#modal-settings > div > form > div.modal-header > div > button")).click(); // 6

        Assert.assertTrue(wd.findElement(By.cssSelector("#email > h2")).isDisplayed()); //7

        Assert.assertTrue(wd.getPageSource().contains("В ожидании новых писем...")); // 8

        wd.findElement(By.cssSelector("#compose")).click(); //9

        WebElement sendButton = wd.findElement(By.cssSelector("#submit"));
        wait.until(ExpectedConditions.visibilityOf(sendButton));
        Assert.assertTrue(sendButton.isDisplayed());
        wd.findElement(By.cssSelector("#to")).sendKeys(email);
        wd.findElement(By.cssSelector("#subject")).sendKeys("Test");
        wd.findElement(By.cssSelector("#text")).sendKeys(secretAddress); //10

        wd.findElement(By.cssSelector("#submit")).click(); //11














    }

}
