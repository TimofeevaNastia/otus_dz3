import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseClass {
    protected static WebDriver driver;
    public WebDriverWait wait;
    public Logger logger = LogManager.getLogger(MarketYandexTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднят");
        /*//если появляется капча
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=~AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--profile-directory=Default");
        driver = new ChromeDriver(options);
        */
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, 5);
        driver.get(cfg.url1());
        driver.manage().window().maximize();
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }
}
