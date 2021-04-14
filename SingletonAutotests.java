import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class SingletonAutotests {

    @BeforeClass
    public static void setUp(){
        Singleton.start();
    }


    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(Singleton.wd, 10);


        Singleton.wd.findElement(By.cssSelector(Consts.CSS_RANDOM_BUTTON)).click(); //2


        Singleton.wd.findElement(By.id(Consts.ID_DOMAIN)).click();
        Singleton.wd.findElement(By.xpath(Consts.XPATH_ROVER_INFO)).click(); //3

        String part1 = Singleton.wd.findElement(By.cssSelector(Consts.CSS_NAME_IN_EMAIL)).getAttribute("value");
        String part2 = Singleton.wd.findElement(By.cssSelector(Consts.CSS_DOMAIN_FIELD)).getAttribute("textContent");
        String email = part1 + part2;//4

        Singleton.wd.findElement(By.id(Consts.ID_SETTINGS)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.XPATH_TEN_MINUTES_BUTTON))).click();
        // wd.findElement(By.xpath("//[@id=\"modal-settings\"]/div/form/div[3]/div[2]/input")).click();  //5

        //  wd.findElement(By.id("pre_settings")).click();
        WebElement time = Singleton.wd.findElement(By.xpath(Consts.XPATH_TEN_MINUTES_TEXT));
        if (time.isSelected())
        {
            System.out.println("10min is checked");
        }
        else
        {
            System.out.println("10min is not checked");
        }
        String secretAddress = Singleton.wd.findElement(By.cssSelector(Consts.CSS_SECRET_ADDRESS)).getAttribute("textContent");
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_CLOSE_SETTINGS_BUTTON)).click(); // 6

        Assert.assertTrue(Singleton.wd.findElement(By.cssSelector(Consts.CSS_MAIN_PAGE_TITLE)).isDisplayed()); //7

        Assert.assertTrue(Singleton.wd.getPageSource().contains("В ожидании новых писем...")); // 8

        Singleton.wd.findElement(By.cssSelector(Consts.CSS_WRITE_BUTTON)).click(); //9

        WebElement sendButton = Singleton.wd.findElement(By.cssSelector(Consts.CSS_SEND_BUTTON));
        wait.until(ExpectedConditions.visibilityOf(sendButton));
        Assert.assertTrue(sendButton.isDisplayed());
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_RECEIVER)).sendKeys(email);
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_LETTER_THEME)).sendKeys("Test");
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_LETTER_TEXT)).sendKeys(secretAddress); //10

        Singleton.wd.findElement(By.cssSelector(Consts.CSS_SEND_BUTTON)).click(); //11

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(Consts.CSS_NEW_LETTER)));
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_NEW_LETTER)).click();   //12

        //  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container-body > div")));
        // wd.findElement(By.cssSelector("#container-body > div")).click();  //12

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Consts.CSS_INCOME_LETTERS)));
        String emailToCheck = Singleton.wd.findElement(By.cssSelector(Consts.CSS_INCOME_LETTERS)).getAttribute("textContent");
        Assert.assertEquals(email, emailToCheck);
        String topicToCheck = Singleton.wd.findElement(By.cssSelector(Consts.CSS_INCOME_TOPIC_LETTER)).getAttribute("textContent");
        Assert.assertEquals("Test", topicToCheck);
        String secretAddressToCheck = Singleton.wd.findElement(By.cssSelector(Consts.CSS_INCOME_SECRET_ADDRESS)).getAttribute("textContent");
        Assert.assertEquals(secretAddress, secretAddressToCheck); //13

        Singleton.wd.findElement(By.cssSelector(Consts.CSS_REPLY_BUTTON)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(Consts.CSS_REPLY_WINDOW)));
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_LETTER_TEXT)).sendKeys("Test2");
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_SEND_BUTTON)).click();//14

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Consts.CSS_INBOX_HEADER)));
        Singleton.wd.navigate().back();//15

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Consts.CSS_IMG_INCOME_LETTER)));//16
        Singleton.wd.findElement(By.cssSelector(Consts.CSS_INCOME_LETTER2)).click();//16 finish

        // String newLetter = wd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).getAttribute("textContent");
        // Assert.assertEquals("Re: Test",  newLetter);
        // wd.findElement(By.cssSelector("#container-body > div > div.inbox > div:nth-child(2) > div > div.subj.col-12.col-md-7.px-md-3")).click(); //16

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Consts.CSS_INBOX_HEADER));
        String newText = Singleton.wd.findElement(By.cssSelector(Consts.CSS_INCOME_SECRET_ADDRESS)).getAttribute("textContent");
        Assert.assertEquals("Test2", newText); //17

        Singleton.wd.findElement(By.cssSelector(Consts.CSS_DELETE_LETTERS)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(Consts.CSS_CONFIRM_DELETE_LETTERS_BUTTON))).click(); //18

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Consts.CSS_INBOX_HEADER)));
        Assert.assertFalse(Singleton.wd.getPageSource().contains("Re: Test")); //19

    }


    @AfterClass
    public static void tearDown(){
        Singleton.quit();
    }
}
