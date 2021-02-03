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
import org.openqa.selenium.chrome.ChromeOptions;
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

    @Test
    public void test1() {
        logger.info("Открыт сайт яндекс");

        modalWin(By.cssSelector("._3Gytho_FnH[data-tid='8ceb3828'] button"));
        logger.info("Закрытие всплывающего окна");

        click(By.cssSelector("[data-zone-data='{\"id\":\"91542578\"}']"));
        logger.info("Переход на вкладку Электроника");

        click(By.xpath("//*[text()='Смартфоны']"));
        logger.info("Выбор пункта Смартфоны");

        modalWin(By.cssSelector("._2NFZmOqZwM [data-tid='42de86b']"));
        logger.info("Ожидание появления уведомления");

        modalWin(By.cssSelector("._1guxbPKMJ6 button"));
        logger.info("Закрытие всплывающего окна");

        click(By.xpath("//*[@id='7893318_153061']/.."));
        logger.info("Выбор Samsung");

        click(By.xpath("//*[@id='7893318_7701962']/.."));
        logger.info("Выбор Xiaomi");

        click(By.cssSelector("[data-autotest-id='dprice']"));
        logger.info("Сортировка по цене");

        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("._2LvbieS_AO._1oZmP3Lbj2"))));
        logger.info("Ожидание прогрузки");

        String css1="._1_ABPFjOJQ";//путь к наименованию плашки "Товар {имя товара} добавлен к сравнению"

        WebElement el5=driver.findElements(By.xpath("//span[contains(text(),'Samsung')]//ancestor::article//div[@class='_1CXljk9rtd']")).get(0);
        click(el5);
        logger.info("Добавили к сравнению первый Samsung");

        String name1=driver.findElements(By.xpath("//*[@data-zone-name='snippetList']//*[contains(text(),'Samsung')]")).get(0).getText();
        String name2=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals("Товар "+name1+" добавлен к сравнению",name2);
        logger.info("Проверка текста после добавления в сравнение Samsung");

        WebElement el4=driver.findElements(By.xpath("//span[contains(text(),'Xiaomi')]//ancestor::article//div[@class='_1CXljk9rtd']")).get(0);
        click(el4);
        logger.info("Добавили к сравнению первый Xiaomi");

        String name3=driver.findElements(By.xpath("//*[@data-zone-name='snippetList']//*[contains(text(),'Xiaomi')]")).get(0).getText();
        String name4=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals("Товар "+name3+" добавлен к сравнению",name4);
        logger.info("Проверка текста после добавления в сравнение Xiaomi");

        click(By.cssSelector(".PaE7W1mhJ5 [data-tid='42de86b']"));
        logger.info("Нажали кнопку сравнить");
        int count = driver.findElements(By.cssSelector("._1P4gDT01yj._34m6bYsU7p ._3B3AAKx4qr div.LwwocgVx0q")).size();
        assertEquals(2,count);
        logger.info("Проверка, что добавлено две позиции");

    }


    public void click(By by){
        WebElement element = wait.until(elementToBeClickable(by));
        int attempts = 0;
        while(attempts < 3) {
            try {
                element.click();
                break;
            }
            catch(ElementClickInterceptedException e) {
                logger.error("Ошибка ElementClickInterceptedException клика на " + by);
            }
            attempts++;
        }
    }

    public void click(WebElement element){
        int attempts = 0;
        while(attempts < 3) {
            try {
                element.click();
                break;
            }
            catch(ElementClickInterceptedException e) {
                logger.error("Ошибка ElementClickInterceptedException клика на " +element);
            }
            attempts++;
        }
    }

    public void modalWin(By by){
        try {
            WebElement win = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            win.click();
        }
        catch(NoSuchElementException e) {
            logger.error("Ошибка NoSuchElementException ");
        }
        catch(TimeoutException e) {
            logger.error("Ошибка TimeoutException ");
        }
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }
}


