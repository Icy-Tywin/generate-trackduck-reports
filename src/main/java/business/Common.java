package business;


//import imgur.Imgur;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import sun.misc.BASE64Encoder;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class Common
{
    public static String resourcePathFolder = "C:\\Automation\\Share";

    public static void checkURL(WebDriver p_driver, String p_expectedURL)
    {
        try
        {
            // check URL
            Thread.sleep(1000);
            String URL = p_driver.getCurrentUrl();
            assertThat(URL, containsString(p_expectedURL));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void checkURLMatch(WebDriver p_driver, String p_expectedURL)
    {
        try
        {
            // check URL
            Thread.sleep(1000);
            String URL = p_driver.getCurrentUrl();
            assertThat(URL, equalTo(p_expectedURL));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void checkhref(WebDriver driver, String p_expected, By searchCriteria)
    {
        String expected = p_expected;
        String actual = driver.findElement(searchCriteria).getAttribute("href");
        assertThat(actual, containsString(expected));
    }

    public static void sendCMD(List<String> cmd) throws Exception
    {
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process pc = pb.start();
        pc.waitFor();
        System.out.println("Done");
    }

    public static String removeEndOfString(String p_inputString)
    {
        StringBuilder sb = new StringBuilder(p_inputString);
        int length = sb.length();
        int startIndex = length / 2;
        String newString = sb.delete(startIndex, length).toString();
        // System.out.println(newString);
        return newString;
    }

    public static void getObject(Object obj) throws IllegalArgumentException, IllegalAccessException
    {
        for (Field field : obj.getClass().getDeclaredFields())
        {
            System.out.println(field.getName() + " - " + field.getType() + " - " + field.get(obj));
        }
        System.out.println();
    }

    public static WebElement waitForElementPresent(WebDriver driver, By searchCriteria) throws InterruptedException
    {
        int loopCount = 0;
        WebElement elementFound = null;

        elementFound = driver.findElement(searchCriteria);

        while (elementFound == null && loopCount < 20)
        {
            Thread.sleep(250);
            elementFound = driver.findElement(searchCriteria);
            loopCount++;
        }

        return elementFound;
    }




    public static void menuHoverClick(WebDriver driver, WebElement[] hoverer, WebElement element)
    {
        // declare new Action
        Actions actions = new Actions(driver);
        // Iterate through the WebElements from the Array
        for (WebElement we : hoverer)
        {
            // hover each of them
            Action hovering = actions.moveToElement(we).build();
            hovering.perform();
        }
        // finally click the WebElement to click at
        element.click();
    }



    public static String readTextFile(String pFilePath)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(pFilePath)))
        {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null)
            {
                //System.out.println(sCurrentLine);
                sb.append(sCurrentLine);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String text = sb.toString();
        System.out.println(text);
        return text;
    }


    public static String dateTimeString()
    {
        String out = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return out;
    }

    public static String dateString()
    {
        String out = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return out;
    }

    public static String createFolder(String pDirectoryPath) throws Exception
    {
        Path filePath = Files.createDirectories(Paths.get(pDirectoryPath));
        return filePath.toString();
    }





}
