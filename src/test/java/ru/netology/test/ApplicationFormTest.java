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
    static void setAppAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void ternDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestForm() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Дуэйн-Гора Камушкин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldValidationFillNameSurname1() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldValidationFillNameSurname2() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Dwayne Johnson");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldValidationFillPhone1() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Дуэйн-Гора Камушкин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldValidationFillPhone2() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Дуэйн-Гора Камушкин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("88005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void shouldTryToNotClickOnTheCheckBox() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Дуэйн-Гора Камушкин");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+78005553535");
        /*driver.findElement(By.cssSelector(".checkbox__box")).click();*/
        driver.findElement(By.cssSelector(".button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }
}

