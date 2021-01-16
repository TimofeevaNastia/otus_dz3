import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static junit.framework.TestCase.assertEquals;

public class FirstTestClass {
    protected static WebDriver driver;
    private Logger logger= LogManager.getLogger(FirstTestClass.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @Test
    public void test(){
        driver.get(cfg.url());
        logger.info("Открыта страница браузера");
        String title= driver.getTitle();
        logger.info("Title страницы: " + title);
        assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям",title);

    }
    @After
    public void setDown(){
        if(driver!=null){
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }
}
