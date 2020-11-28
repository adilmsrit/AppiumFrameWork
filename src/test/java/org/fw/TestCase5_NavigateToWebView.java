package org.fw;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class TestCase5_NavigateToWebView extends base {
    /* Test Case automated below
     * Validate Mobile gestures working for Links(longPress) and navigate to WebView
     */
    @Test
    public void validateOrdeinCheckoutPage() throws IOException, InterruptedException {
        String apkFile = "GeneralStoreApp";

        AndroidDriver<AndroidElement> driver = capabilities(apkFile);
        driver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys("Helllooo");
        driver.hideKeyboard();
        driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();

        List<AndroidElement> AddToCartElements = driver.findElements(By.xpath("//*[@text='ADD TO CART']"));

        for (AndroidElement element : AddToCartElements) {
            element.click();
        }

        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();

        Thread.sleep(2000);
        List<AndroidElement> amountOfItemsInCart = driver.findElementsById("com.androidsample.generalstore:id/productPrice");

        Double total = 0.00;
        for (AndroidElement items : amountOfItemsInCart) {
            total = total + Double.parseDouble(items.getText().substring(1));
        }

        Double totalFromApp = Double.parseDouble(driver.findElementById("com.androidsample.generalstore:id/totalAmountLbl").getText().substring(1));

        Assert.assertEquals(total, totalFromApp);


        WebElement checkBox = driver.findElementByClassName("android.widget.CheckBox");

        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(TapOptions.tapOptions().withElement(ElementOption.element(checkBox))).perform();

        WebElement terms = driver.findElementById("com.androidsample.generalstore:id/termsButton");
        touchAction.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(terms))
                .withDuration(Duration.ofSeconds(5))).perform();
        driver.findElementById("android:id/button1").click();
        Thread.sleep(1000);
        driver.findElementById("com.androidsample.generalstore:id/btnProceed").click();

        Thread.sleep(3000);
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }


    }

}
