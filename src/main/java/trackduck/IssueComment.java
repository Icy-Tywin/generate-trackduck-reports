package trackduck;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static trackduck.GenerateData.driver;

public class IssueComment
{
    public String Author = "";
    public String Date = "";
    public String Text = "";
    public List<CommentAttachment> Attachments = new ArrayList<CommentAttachment>();

    public IssueComment(String commentListPosition)
    {
        try
        {
            this.Author = getAuthor(commentListPosition);
            this.Date = getDate(commentListPosition);
            this.Text = getText(commentListPosition);

            //List<CommentAttachment> attachmentList;
            List<WebElement> commentAttachments = returnCommentAttachments(commentListPosition);

            int commentAttachmentLoopCount = 1;
            for (WebElement commentAttachment : commentAttachments)
            {
                String commentAttachmentListPosition = "[" + commentAttachmentLoopCount + "]";
                CommentAttachment attachment = new CommentAttachment(commentListPosition, commentAttachmentListPosition);
                this.Attachments.add(attachment);
                commentAttachmentLoopCount++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public IssueComment()
    {

    }


    public String getAuthor(String commentListPosition)
    {
        String author = "";
        String authorXpath = ObjRepo.commentXpath + commentListPosition + ObjRepo.commentAuthorXpath;
        String text = "";
        text = driver.findElement(By.xpath(authorXpath)).getAttribute("textContent");
        author = text;

        if (!author.equals(""))
            author = author.substring(3);
        if (author.contains("                                       "))
        {
            author = author.substring(107);
            author = author.substring(0, author.length() - 37);
        }
        if (isSystemComment(commentListPosition))
            author = "SYSTEM";
        return author;
    }

    public String getDate(String commentListPosition)
    {
        String dateXpath = ObjRepo.commentXpath + commentListPosition + ObjRepo.commentDateXpath;
        String date = driver.findElement(By.xpath(dateXpath)).getAttribute("textContent");
        return date;
    }

    public String getText(String commentListPosition)
    {
        String commentText = "";
        if (!isSystemComment(commentListPosition))
        {
            String textXpath = ObjRepo.commentXpath + commentListPosition + ObjRepo.commentTextXpath;
            commentText = driver.findElement(By.xpath(textXpath)).getAttribute("textContent");
        }
        else
        {
            String textXpath = ObjRepo.commentXpath + commentListPosition + ObjRepo.commentSystemXpath + ObjRepo.commentSystemTextXpath;
            commentText = driver.findElement(By.xpath(textXpath)).getAttribute("textContent");
        }
        return commentText;
    }

    public static List<WebElement> returnIssueComments()
    {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> issueComments = driver.findElements(By.xpath(ObjRepo.commentXpath));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return issueComments;
    }

    public Boolean isSystemComment(String commentListPosition)
    {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Boolean isSystemComment = false;
        try
        {
            WebElement element = driver.findElement(By.xpath(ObjRepo.commentXpath + commentListPosition + ObjRepo.commentSystemXpath));
            isSystemComment = true;
        }
        catch (Exception ex)
        {
            isSystemComment = false;
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return isSystemComment;
    }

    public List<WebElement> returnCommentAttachments(String commentListPosition)
    {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> commentAttachments = driver.findElements(By.xpath(ObjRepo.commentXpath + commentListPosition + ObjRepo.commentAttachmentXpath));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return commentAttachments;
    }

}
