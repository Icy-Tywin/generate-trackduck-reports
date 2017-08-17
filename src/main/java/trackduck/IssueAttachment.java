package trackduck;

import business.Image;
import dropbox.Dropbox;
import imgur.Imgur;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static trackduck.GenerateData.driver;

public class IssueAttachment
{
    public String Url;
    public String Text;

    public IssueAttachment(String attachmentListPosition) throws Exception
    {
        this.Url = getUrl(attachmentListPosition);
        this.Text = driver.findElement(By.xpath(ObjRepo.attachmentXpath + attachmentListPosition + ObjRepo.attachmentTextXpath)).getAttribute("textContent");
    }

    public String getUrl(String attachmentListPosition)
    {
        String url = driver.findElement(By.xpath(ObjRepo.attachmentXpath + attachmentListPosition + ObjRepo.attachmentUrlXpath)).getAttribute("href");

        // Trackduck have changed the URL's for the attachments to have crap on the end so I have to strip it off before I try to upload it to Imgur or Dropbox
        int questionIndex = url.indexOf("?");
        url = url.substring(0, questionIndex);

        // if the file is an image upload it to Imgur, if it's anything else upload it to Dropbox
        if (Image.isFileImageExtension(url))
        {
            Imgur imgur = new Imgur();
            url = imgur.uploadImage(url, Imgur.ALBUM_ID_TRACKDUCK_REPORTS, "url");
        }
        else
        {
            Dropbox dropbox = new Dropbox();
            url = dropbox.uploadReportFile(url);
        }

        return url;
    }


    public IssueAttachment()
    {

    }

    public static List<WebElement> returnIssueAttachments()
    {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> issueAttachments = driver.findElements(By.xpath(ObjRepo.attachmentXpath));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return issueAttachments;
    }
}
