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
        wd.get("https://tempmail.plus/ru/");
        wd.manage().window().maximize(); //1

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

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container-body > div > div.inbox > div.mail")));
        wd.findElement(By.cssSelector("#container-body > div > div.inbox > div.mail")).click();   //12

      //  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container-body > div")));
       // wd.findElement(By.cssSelector("#container-body > div")).click();  //12

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#info > div.row.row-info.no-gutters > div.col.d-flex.mb-10")));
        String emailToCheck = wd.findElement(By.cssSelector("#info > div.row.row-info.no-gutters > div.col.d-flex.mb-10")).getAttribute("textContent");
        Assert.assertEquals(email, emailToCheck);
        String topicToCheck = wd.findElement(By.cssSelector("#info > div.subject.mb-20")).getAttribute("textContent");
        Assert.assertEquals("Test", topicToCheck);
        String secretAddressToCheck = wd.findElement(By.cssSelector("#info > div.overflow-auto.mb-20")).getAttribute("textContent");
        Assert.assertEquals(secretAddress, secretAddressToCheck); //13

        wd.findElement(By.cssSelector("#reply")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#form > div.modal-header.shadow-sm > div.bar > span")));
        wd.findElement(By.cssSelector("#text")).sendKeys("Test2");
        wd.findElement(By.cssSelector("#submit")).click();//14

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#inbox-head > p")));
        wd.navigate().back();//15

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.from.col-9.col-md-4 > img")));//16
        wd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).click();//16 finish

        // String newLetter = wd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).getAttribute("textContent");
         // Assert.assertEquals("Re: Test",  newLetter);
        // wd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).click(); //16

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#inbox-head > p")));
        String newText = wd.findElement(By.cssSelector("#info > div.overflow-auto.mb-20")).getAttribute("textContent");
        Assert.assertEquals("Test2", newText); //17

        wd.findElement(By.cssSelector("#delete_mail")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#confirm_mail"))).click(); //18

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#inbox-head > p")));
        Assert.assertFalse(wd.getPageSource().contains("Re: Test")); //19

        wd.quit();
    }

}
