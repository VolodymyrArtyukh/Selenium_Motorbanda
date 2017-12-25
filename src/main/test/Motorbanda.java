import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import java.util.concurrent.TimeUnit;


import static org.testng.Assert.assertEquals;


public class Motorbanda
{

    private static WebDriver driver;


    @BeforeClass
    public void setUp()
    {

        driver = new ChromeDriver();                    //selecting Chrome browser
        driver.manage().window().maximize();            //making full size screen
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);        //waiting for 15 seconds
        driver.get("https://motobanda.pl/sklep");      //entering the website

    }



    @Test(priority = 1)
    public void StepsForBuying()
    {

        String linkTextRekawice = "Rękawice";
        String cssTekstylia = "#\\31 4 > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > span:nth-child(2)";
        String cssHelmetAssert = ".title > a:nth-child(1)";
        String cssClickingGlove = ".lazy";

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); //waiting for 15 seconds till the page loads

        JavascriptExecutor scrollDown = (JavascriptExecutor) driver; //creating "scrollDown" object
        scrollDown.executeScript("scroll(0, 200)", ""); //scrolling down

        //finding "gloves" and making a click;
        WebElement gloves = driver.findElement(By.linkText(linkTextRekawice));
        gloves.click();

        scrollDown.executeScript("scroll(0, 600)", ""); //scrolling up

        //finding the "Materiał tekstylia" and clicking on it
        WebElement textile = driver.findElement(By.cssSelector(cssTekstylia));
        textile.click();

        //asserting that the element is displayed by the helmets text
        assertEquals("HELD EVO THRUX","HELD EVO THRUX", driver.findElement(By.cssSelector(cssHelmetAssert)).getText());

        //clicking on our glove
        WebElement clickingGloves = driver.findElement(By.cssSelector(cssClickingGlove));
        clickingGloves.click();
    }




        @Test(priority = 2)
        public void buyingPage()
    {

        String cssBuyNow = "a.btn-primary-hollow";
        String cssEditToCart = "button.btn:nth-child(6)";
        String cssNaklejkaMB = "li.bonus:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)";
        String cssBrelokMB = "li.bonus:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)";
        String cssNaklejkaPrice = "li.bonus:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(2)";
        String cssBrelokPrice = "li.bonus:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > span:nth-child(2)";

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); //waiting for 15 seconds till the page loads

        JavascriptExecutor scrollDown = (JavascriptExecutor) driver; //creating "scrollDown" object
        scrollDown.executeScript("scroll(0, 400)", "");

        //clicking on "Kup Teraz"
        WebElement buy = driver.findElement(By.cssSelector(cssBuyNow));
        buy.click();

        scrollDown.executeScript("scroll(0, 1700)", "");

        //clicking "Wrzuc do kosza"
        WebElement cartEdit = driver.findElement(By.cssSelector(cssEditToCart));
        cartEdit.click();

        //making sure that Naklejka and Brelok are located
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssNaklejkaMB)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssBrelokMB)));

        //making sure that actual and expected prices are 0 zl
        assertEquals("0 zł", "0 zł",  driver.findElement(By.cssSelector(cssNaklejkaPrice)).getText());
        assertEquals("0 zł","0 zł", driver.findElement(By.cssSelector(cssBrelokPrice)).getText());


    }


    @AfterClass
    public void tearDown() throws InterruptedException
    {

        Thread.sleep(5000);     //waiting 5 seconds before the browser closes

        if (driver != null)         //if driver is present
        {
            driver.quit();          //closing Chrome
        }

    }

}

