package trackduck;

import imgur.Imgur;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static trackduck.GenerateData.driver;

public class Issue
{
    public String Id;
    public String Description;
    public String Screenshot;
    public String Date;
    public String Author;
    public String Source;
    public String Browser;
    public String OperatingSystem;
    public String Resolution;
    public Boolean Closed;
    public Boolean Priority;

    public List<IssueAttachment> IssueAttachments = new ArrayList<IssueAttachment>();
    public List<IssueComment> IssueComments = new ArrayList<IssueComment>();

    public Issue(String issueListPosition) throws Exception
    {
        try
        {
            issueListPosition = "[" + issueListPosition + "]";
            this.Id = driver.findElement(By.xpath(ObjRepo.issueXpath + issueListPosition + ObjRepo.issueIdXpath)).getText();
            this.Description = driver.findElement(ObjRepo.issueDescription).getText();

            String originalImage = driver.findElement(ObjRepo.issueScreenshot).getAttribute("src");
            Imgur imgur = new Imgur();
            this.Screenshot = imgur.uploadImage(originalImage, Imgur.ALBUM_ID_TRACKDUCK_REPORTS, "url");
            this.Date = driver.findElement(By.xpath(ObjRepo.issueXpath + issueListPosition + ObjRepo.issueDateXpath)).getText();
            this.Author = driver.findElement(ObjRepo.issueAuthor).getText();
            this.Source = driver.findElement(ObjRepo.issueSource).getText();
            this.Browser = driver.findElement(ObjRepo.issueBrowser).getText();
            this.OperatingSystem = driver.findElement(ObjRepo.issueOS).getText();
            this.Resolution = driver.findElement(ObjRepo.issueRes).getText();

            String status = driver.findElement(ObjRepo.issueClosed).getText();
            if (status.contains("Mark closed"))
                this.Closed = false;
            else if (status.contains("Mark open"))
                this.Closed = true;

            String priorityStatus = driver.findElement(ObjRepo.issuePriority).getText();
            if (priorityStatus.contains("Set priority"))
                this.Priority = false;
            else if (priorityStatus.contains("Remove priority"))
                this.Priority = true;

            //Issue attachments
            String issueAttachmentPosition = "[1]";
            int attachmentLoopCount = 1;
            List<WebElement> issueAttachmentItems = IssueAttachment.returnIssueAttachments();
            for (WebElement issueAttachmentItem : issueAttachmentItems)
            {
                issueAttachmentPosition = "[" + Integer.toString(attachmentLoopCount) + "]";
                IssueAttachment issueAttachment = new IssueAttachment(issueAttachmentPosition);
                this.IssueAttachments.add(issueAttachment);

                attachmentLoopCount++;
            }

            //Issue Comments
            String issueCommentPosition = "[1]";
            int commentLoopCount = 1;
            List<WebElement> issueCommentItems = IssueComment.returnIssueComments();
            for (WebElement issueCommentItem : issueCommentItems)
            {
                issueCommentPosition = "[" + Integer.toString(commentLoopCount) + "]";
                IssueComment issueComment = new IssueComment(issueCommentPosition);
                this.IssueComments.add(issueComment);

                commentLoopCount++;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public Issue()
    {

    }

    public Issue(String pDescription, String pScreenshot)
    {
        this.Description = pDescription;
        this.Screenshot = pScreenshot;
    }

    //have to add these getters for freemarker
    public String getId()
    {
        return Id;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getScreenshot()
    {
        return Description;
    }

    public String getDate()
    {
        return Date;
    }

    public String getAuthor()
    {
        return Author;
    }

    public String getSource()
    {
        return Source;
    }

    public String getBrowser()
    {
        return Browser;
    }

    public String getOperatingSystem()
    {
        return OperatingSystem;
    }

    public String getResolution()
    {
        return Resolution;
    }

    public Boolean getClosed()
    {
        return Closed;
    }

    public Boolean getPriority()
    {
        return Priority;
    }

    public List<IssueAttachment> getIssueAttachments()
    {
        return IssueAttachments;
    }

    public List<IssueComment> getIssueComments()
    {
        return IssueComments;
    }


    public static List<WebElement> returnIssueItems()
    {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> issueItems = driver.findElements(By.xpath(ObjRepo.issueXpath));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return issueItems;
    }

    //actions
    public static void filterShowClosed()
    {
        WebElement btnFilter = driver.findElement(ObjRepo.btnFilter);

        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        //((JavascriptExecutor) driver).executeScript(mouseOverScript, btnFilter);
        btnFilter.click();
        driver.findElement(ObjRepo.btnShowClosed).click();
    }

    public static void sortIssues()
    {
        driver.findElement(ObjRepo.btnSortIssues).click();
    }

    public static void selectIssue(String issueListPosition)
    {
        WebElement issueitem = driver.findElement(By.xpath(ObjRepo.issueXpath + issueListPosition));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", issueitem);
        //Thread.sleep(500);
        issueitem.click();
    }

}
