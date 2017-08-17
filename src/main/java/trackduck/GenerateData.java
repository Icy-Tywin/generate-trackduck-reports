package trackduck;

import business.Common;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenerateData
{
    // TODO - read these from appsettings.xml
    //===CONFIGURE THESE SETTINGS FOR EACH PROJECT===
    public String PROJECTURL = "https://app.trackduck.com/project/59358e8a834edffb458ba89d/issue";
    public static String PROJECTNAME = "Client name - Code - Project name";
    public static String PROJECTSTARTDATE = "05/06/2017";
    public static String PROJECTENDDATE = "23/06/2017";
    //===================

    public static WebDriver driver;
    public static String REPORTPATH = "C:/Automation/TrackDuck/GeneratedReports/";

    public String baseUrl = "https://app.trackduck.com/auth/login";
    public String nodeUrl = "http://localhost:4444/wd/hub";
    public String browserName;
    public Boolean showClosedIssues = true;

    // ==== CREATE YOUR REPORT ==== \\
    @Test
    public void createReport() throws Exception
    {
        List<Issue> issues = new ArrayList<Issue>();
        try
        {
            issues = printList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            GenerateHTML generateReport = new GenerateHTML();
            generateReport.generateHTML(issues, PROJECTNAME);
        }
    }


    //region tests

    @Test
    public List<Issue> generateList() throws Exception
    {
        String issuePosition = "[1]";
        login();
        Thread.sleep(1000);
        driver.get(PROJECTURL);

        //Commenting this out because I'm going to process open issues first and then closed
        //if (showClosedIssues) Issue.filterShowClosed();


        //int numberOfIssues = issueItems.size();
        //if (Common.debugInfo) System.out.println("Number of issues = " + numberOfIssues);

        List<Issue> issues = null;
        try
        {
            issues = new ArrayList<Issue>();

            //process open issues first
            Issue.sortIssues();
            List<WebElement> issueItems = Issue.returnIssueItems();
            //Number to start from for open issues, usually 1
            int loopCount = 1;
            List<Issue> openIssues = fillIssuesList(issueItems, loopCount);
            if (openIssues.size() > 0)
                issues.addAll(openIssues);

            //process closed issues
            if (showClosedIssues)
            {
                driver.get(PROJECTURL);
                Issue.filterShowClosed();
                Issue.sortIssues();
                // uncomment this wait if you are wanting to start the report further down the list
                // this gives you a chance to scroll the page down
                //Thread.sleep(30000);
                issueItems = Issue.returnIssueItems();
                //Number to start from for open issues, usually 1 ***********
                loopCount = 1;
                List<Issue> closedIssues = fillIssuesList(issueItems, loopCount);
                if (closedIssues.size() > 0)
                    issues.addAll(closedIssues);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return issues;
    }


    @Test
    public List<Issue> printList() throws Exception
    {
        //List<Issue> issues = null;
        List<Issue> issues = new ArrayList<Issue>();

        try
        {
            issues = generateList();

            //GENERAL INFO
            if (Common.debugInfo)
                System.out.println("Number of issues = " + issues.size());
            if (Common.debugInfo)
                System.out.println("Date = " + new SimpleDateFormat("dd/MM/yyy").format(new Date()));

            //ISSUE
            int issueLoopCount = 1;
            for (Issue issue : issues)
            {
                if (Common.debugInfo)
                    System.out.println("=========ISSUE " + issueLoopCount + " DETAILS=========");
                if (Common.debugInfo)
                    System.out.println("Id = " + issue.Id);
                if (Common.debugInfo)
                    System.out.println("Description = " + issue.Description);
                if (Common.debugInfo)
                    System.out.println("Screenshot = " + issue.Screenshot);
                if (Common.debugInfo)
                    System.out.println("Date = " + issue.Date);
                if (Common.debugInfo)
                    System.out.println("Author = " + issue.Author);
                if (Common.debugInfo)
                    System.out.println("Source = " + issue.Source);
                if (Common.debugInfo)
                    System.out.println("Browser = " + issue.Browser);
                if (Common.debugInfo)
                    System.out.println("OS = " + issue.OperatingSystem);
                if (Common.debugInfo)
                    System.out.println("Resolution = " + issue.Resolution);
                if (Common.debugInfo)
                    System.out.println("Closed = " + issue.Closed);
                if (Common.debugInfo)
                    System.out.println("Priority = " + issue.Priority);

                //ATTACHMENTS
                int attachmentLoopCount = 1;
                for (IssueAttachment issueAttachment : issue.IssueAttachments)
                {
                    if (Common.debugInfo)
                        System.out.println("======ISSUE " + issueLoopCount + " ATTACHMENT " + attachmentLoopCount + " DETAILS======");
                    if (Common.debugInfo)
                        System.out.println("Attachment Url = " + issueAttachment.Url);
                    if (Common.debugInfo)
                        System.out.println("Attachment text = " + issueAttachment.Text);
                    attachmentLoopCount++;
                }

                //COMMENTS
                int commentLoopCount = 1;
                for (IssueComment comment : issue.IssueComments)
                {
                    if (Common.debugInfo)
                        System.out.println("=====ISSUE " + issueLoopCount + " COMMENT " + commentLoopCount + " DETAILS=====");
                    if (Common.debugInfo)
                        System.out.println("Comment Author = " + comment.Author);
                    if (Common.debugInfo)
                        System.out.println("Comment Date = " + comment.Date);
                    if (Common.debugInfo)
                        System.out.println("Comment Text = " + comment.Text);

                    //COMMENT ATTACHMENTS
                    int commentAttachmentLoopCount = 1;
                    for (CommentAttachment commentAttachment : comment.Attachments)
                    {
                        if (Common.debugInfo)
                            System.out.println("====ISSUE " + issueLoopCount + " COMMENT " + commentLoopCount + " ATTACHMENT " + commentAttachmentLoopCount + " DETAILS====");
                        if (Common.debugInfo)
                            System.out.println("Comment Attachment Url = " + commentAttachment.Url);

                        if (Common.debugInfo)
                            System.out.println("Comment Attachment Text = " + commentAttachment.Text);
                        commentAttachmentLoopCount++;
                    }
                    commentLoopCount++;
                }
                issueLoopCount++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return issues;
    }

    public List<Issue> fillIssuesList(List<WebElement> issueItems, int loopCount) throws Exception
    {
        String issuePosition;
        Actions actions = new Actions(driver);
        List<Issue> issues = new ArrayList<Issue>();

        //System.out.println(issueItems.size());

        try
        {
            if (issueItems.size() > 0)
            {
                for (int x = loopCount - 1; x < issueItems.size(); x++)
                //for (WebElement issueItem : issueItems)
                {
                    System.out.println(loopCount);
                    issuePosition = "[" + Integer.toString(loopCount) + "]";
                    Issue.selectIssue(issuePosition);
                    Issue issue = new Issue(Integer.toString(loopCount));
                    issues.add(issue);

                    int mod = (loopCount) % 25;

                    if (loopCount != 0 && mod == 0)
                    {
                        actions.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_DOWN).perform();
                        Thread.sleep(1500);
                        List<WebElement> issueItems2 = Issue.returnIssueItems();
                        //System.out.println(issueItems2.size());

                        if (issueItems2.size() != issueItems.size())
                        {
                            issues.addAll(fillIssuesList(issueItems2, loopCount + 1));
                        }
                    }
                    loopCount++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return issues;
    }


    //endregion


    public void login() throws Exception
    {
        driver.findElement(ObjRepo.txtLoginEmail).sendKeys(ObjRepo.loginEmail);
        driver.findElement(ObjRepo.txtLoginPassword).sendKeys(ObjRepo.loginPassword);
        Thread.sleep(500);
        driver.findElement(ObjRepo.btnLoginButton).click();
    }


    @Parameters({"browserName", "platform"})
    @BeforeMethod
    public void setUp(@Optional("firefox") String pBrowserName, @Optional("WINDOWS") String pPlatform) throws Exception
    {
        browserName = pBrowserName;
        DesiredCapabilities capability = null;

        // set browser
        if (pBrowserName.equals("firefox"))
            capability = DesiredCapabilities.firefox();
        else if (pBrowserName.equals("chrome"))
            capability = DesiredCapabilities.chrome();
        else if (pBrowserName.equals("iexplore"))
            capability = DesiredCapabilities.internetExplorer();
        else if (pBrowserName.equals("opera"))
            capability = DesiredCapabilities.operaBlink();

        // set platform
        if (pPlatform.equals("XP"))
            capability.setPlatform(Platform.XP);
        else if (pPlatform.equals("WINDOWS"))
            capability.setPlatform(Platform.WINDOWS);

        // start driver
        driver = new RemoteWebDriver(new URL(nodeUrl), capability);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // open URL
        driver.get(baseUrl);

        // check URL
        Common.checkURL(driver, baseUrl);
    }


    @AfterMethod(alwaysRun = true)
    public void teardown()
    {
        // close browser
        driver.quit();
    }

}
