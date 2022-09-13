package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationFormTest {

    private WebDriver driver;
    @BeforeAll
    static void setUpAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp(){
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    void tearsDown(){
        driver.quit();
        driver=null;
    }
    @Test
    void shouldTestForm(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Дуэйн Камушкин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals(text.trim(),"Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
    }
}
