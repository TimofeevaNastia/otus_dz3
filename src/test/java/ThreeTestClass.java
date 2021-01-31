import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class ThreeTestClass {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(ThreeTestClass.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднят");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, 3);
        driver.get(cfg.url1());
        driver.manage().window().maximize();
    }

    @Test
    public void test1() throws InterruptedException {
        logger.info("Открыт сайт яндекс");
        driver.findElement(By.cssSelector("[data-zone-data='{\"id\":\"91542578\"}']")).click();
        logger.info("Переход на вкладку Электроника");
        driver.findElement(By.xpath("//*[text()='Смартфоны']")).click();
        logger.info("Выбор пункта Смартфоны");
        driver.findElement(By.xpath("//*[@id='7893318_153061']/..")).click();
        logger.info("Выбор Samsung");
        driver.findElement(By.xpath("//*[@id='7893318_7701962']/..")).click();
        logger.info("Выбор Xiaomi");
        driver.findElement(By.cssSelector("[data-autotest-id='dprice']")).click();
        logger.info("Сортировка по цене");
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("._2LvbieS_AO._1oZmP3Lbj2"))));
        logger.info("Ожидание прогрузки");
        String css1="._1_ABPFjOJQ";//путь к наименованию плашки "Товар {имя товара} добавлен к сравнению"
        driver.findElements(By.xpath("//span[contains(text(),'Samsung')]//ancestor::article//div[@class='_1CXljk9rtd']")).get(0).click();;
        logger.info("Добавили к сравнению первый Samsung");
        String name1=driver.findElements(By.xpath("//*[@data-tid='ce80a508' and contains(text(),'Samsung')]")).get(0).getText();
        String name2=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals("Товар "+name1+" добавлен к сравнению",name2);
        logger.info("Проверка текста после добавления в сравнение Samsung");
        driver.findElements(By.xpath("//span[contains(text(),'Xiaomi')]//ancestor::article//div[@class='_1CXljk9rtd']")).get(0).click();;
        logger.info("Добавили к сравнению первый Xiaomi");
        String name3=driver.findElements(By.xpath("//*[@data-tid='ce80a508' and contains(text(),'Xiaomi')]")).get(0).getText();
        String name4=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals("Товар "+name3+" добавлен к сравнению",name4);
        logger.info("Проверка текста после добавления в сравнение Xiaomi");
        Thread.sleep(1000);
        WebElement el3 = driver.findElement(By.cssSelector(".PaE7W1mhJ5 [data-tid='42de86b']"));
        click(el3);
        logger.info("Нажали кнопку сравнить");
        int count = driver.findElements(By.cssSelector("._1P4gDT01yj._34m6bYsU7p ._3B3AAKx4qr div.LwwocgVx0q")).size();
        assertEquals(2,count);
        logger.info("Проверка, что добавлено две позиции");
    }


    public void click(WebElement element){
        wait.until(elementToBeClickable(element));
        element.click();
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }
}


