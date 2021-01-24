import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class TwoTestClass {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(TwoTestClass.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void test1(){
        driver.get(cfg.url1());
        driver.manage().window().maximize();
        logger.info("Открыт сайт яндекс");
        String title = driver.getTitle();
        logger.info("Title страницы: " + title);
        assertEquals("Яндекс", title);
    }

    @Test
    public void test2(){
        driver.get(cfg.url2());
        logger.info("Переход на сайт теле2");
        driver.findElement(By.id("searchNumber")).sendKeys("97");
        logger.info("Поиск номера ");
        new WebDriverWait(driver, 15).ignoring(NoSuchElementException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loader-overlay")));
        logger.info("Номера найдены");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }
}


