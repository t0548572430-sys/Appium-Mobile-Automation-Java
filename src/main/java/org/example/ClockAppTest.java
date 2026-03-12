package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ClockAppTest {

    static AndroidDriver driver;

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("android");
        options.setDeviceName("emulator-5554");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.google.android.deskclock");
        options.setAppActivity("com.android.deskclock.DeskClock");
        options.setNoReset(false);
        options.amend("appium:adbExecutable", "C:\\Users\\user1\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
    }

    @Test
    public void testTimer() {
        // 1. הגדרת זמן
        driver.findElement(By.id("com.google.android.deskclock:id/tab_menu_timer")).click();
        driver.findElement(By.id("com.google.android.deskclock:id/timer_setup_digit_3")).click();
        driver.findElement(By.id("com.google.android.deskclock:id/timer_setup_digit_0")).click();

        // 2. הפעלה
        driver.findElement(By.id("com.google.android.deskclock:id/fab")).click();
        System.out.println("Timer started!");

        // 3. המתנה לסיום
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. עצירה
        WebDriverWait finalWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement stopBtn = finalWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(@text, 'Stop') or contains(@content-desc, 'Stop') or @id='com.google.android.deskclock:id/left_button']")));
            stopBtn.click();
        } catch (Exception e) {
            driver.findElement(By.id("com.google.android.deskclock:id/fab")).click();
        }

        // 5. איפוס סופי (Reset)
        try {
            driver.findElement(By.id("com.google.android.deskclock:id/left_button")).click();
        } catch (Exception e) {}
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}