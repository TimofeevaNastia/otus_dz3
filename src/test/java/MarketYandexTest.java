import org.junit.Test;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;


import static junit.framework.TestCase.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class MarketYandexTest extends BaseClass {

    @Test
    public void test1() {
        String button = "._3Gytho_FnH[data-tid='8ceb3828'] button";
        String electronics = "[data-zone-data='{\"id\":\"91542578\"}']";
        String smartphone = "//*[text()='Смартфоны']";
        String check_samsung = "//*[@id='7893318_153061']/..";
        String check_xiaomi = "//*[@id='7893318_7701962']/..";
        String sorting = "[data-autotest-id='dprice']";
        String win_load="._2LvbieS_AO._1oZmP3Lbj2";
        String list_product="//span[contains(text(),'%s')]//ancestor::article//div[@class='_1CXljk9rtd']";//первый товар с указанным именем
        String list_product_name="//*[@data-zone-name='snippetList']//*[contains(text(),'%s')]";
        String btn_compare=".PaE7W1mhJ5 [data-tid='42de86b']";
        String list_product_compare="._1P4gDT01yj._34m6bYsU7p ._3B3AAKx4qr div.LwwocgVx0q";

        String window_notification_btn1="._2NFZmOqZwM [data-tid='42de86b']";
        String window_notification_btn2="._1guxbPKMJ6 button";


        logger.info("Открыт сайт яндекс");

        modalWin(By.cssSelector(button));
        logger.info("Закрытие всплывающего окна");

        click(By.cssSelector(electronics));
        logger.info("Переход на вкладку Электроника");

        click(By.xpath(smartphone));
        logger.info("Выбор пункта Смартфоны");

        modalWin(By.cssSelector(window_notification_btn1));
        logger.info("Закрытие всплывающего окна");

        modalWin(By.cssSelector(window_notification_btn2));
        logger.info("Закрытие всплывающего окна");

        click(By.xpath(check_samsung));
        logger.info("Выбор Samsung");

        click(By.xpath(check_xiaomi));
        logger.info("Выбор Xiaomi");

        click(By.cssSelector(sorting));
        logger.info("Сортировка по цене");

        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(win_load))));
        logger.info("Ожидание прогрузки");

        String css1="._1_ABPFjOJQ";//путь к наименованию плашки "Товар {имя товара} добавлен к сравнению"

        String first_samsung=String.format(list_product,"Samsung");
        WebElement add_samsung=driver.findElements(By.xpath(first_samsung)).get(0);
        click(add_samsung);
        logger.info("Добавили к сравнению первый Samsung");

        String product_name_samsung=String.format(list_product_name,"Samsung");
        String name1=driver.findElements(By.xpath(product_name_samsung)).get(0).getText();
        String name2=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals(String.format("Товар %s добавлен к сравнению",name1),name2);
        logger.info("Проверка текста после добавления в сравнение Samsung");

        String first_xiaomi=String.format(list_product,"Xiaomi");
        WebElement add_xiaomi=driver.findElements(By.xpath(first_xiaomi)).get(0);
        click(add_xiaomi);
        logger.info("Добавили к сравнению первый Xiaomi");

        String product_name_xiaomi=String.format(list_product_name,"Xiaomi");
        String name3=driver.findElements(By.xpath(product_name_xiaomi)).get(0).getText();
        String name4=driver.findElement(By.cssSelector(css1)).getText();
        assertEquals(String.format("Товар %s добавлен к сравнению",name3),name4);
        logger.info("Проверка текста после добавления в сравнение Xiaomi");

        click(By.cssSelector(btn_compare));
        logger.info("Нажали кнопку сравнить");
        int count = driver.findElements(By.cssSelector(list_product_compare)).size();
        assertEquals(2,count);
        logger.info("Проверка, что добавлено две позиции");

    }


    private void click(By by){
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

    private void click(WebElement element){
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

    private void modalWin(By by){
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


}


