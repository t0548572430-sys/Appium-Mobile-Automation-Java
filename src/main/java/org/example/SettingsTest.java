package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

enum SettingsMenu {
    SYSTEM("System"),
    ABOUT("About emulated device");

    private final String label;
    SettingsMenu(String label) { this.label = label; }
    public String getLabel() { return label; }
}

public class SettingsTest {
    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setPlatformName("Android")
                .setAppPackage("com.android.settings")
                .setAppActivity("com.android.settings.Settings")
                .setNoReset(false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @Test
    @DisplayName("Mission 2: Settings Navigation")
    public void testSettingsTask() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // שלב א': לחיצה על System
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().text(\"" + SettingsMenu.SYSTEM.getLabel() + "\"))")).click();

        System.out.println("Entered System menu.");

        // : חזרה אחורה לתפריט הראשי (כי ה-About נמצא שם)
        driver.navigate().back();
        System.out.println("Going back to main settings...");

        WebElement aboutButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"About\"))")));

        aboutButton.click();
        System.out.println("Entered About Device.");

        //  אימות סופי
        WebElement aboutHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().textContains(\"device\")")));

        Assertions.assertTrue(aboutHeader.isDisplayed(), "The About page title was not found!");
        System.out.println("Mission 2: Task completed and verified!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}