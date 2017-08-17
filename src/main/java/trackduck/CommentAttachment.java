package trackduck;

import business.Image;
import dropbox.Dropbox;
import imgur.Imgur;
import org.openqa.selenium.By;

import static trackduck.GenerateData.driver;

public class CommentAttachment
{
    public String Url;
    public String Text;

    public CommentAttachment(String commentListPosition, String attachmentListPosition)
    {
        this.Url = getUrl(commentListPosition, attachmentListPosition);
        this.Text = driver.findElement(By.xpath(ObjRepo.commentXpath + commentListPosition + ObjRepo.commentAttachmentXpath + attachmentListPosition + ObjRepo.attachmentTextXpath)).getAttribute("textContent");
    }

    public CommentAttachment()
    {
        this.Url = "http://www.google.co.uk/";
        this.Text = "Default Comment Attachment Text";
    }


    public String getUrl(String commentListPosition, String attachmentListPosition)
    {
        String newUrl = "";
        String url = driver.findElement(By.xpath("(" + ObjRepo.commentXpath + commentListPosition + ObjRepo.commentAttachmentXpath + ObjRepo.commentAttachmentUrlXpath + ")" + attachmentListPosition)).getAttribute("href");

        //Trackduck have changed the URL's for the attachments to have crap on the end so I have to strip it off before I try to upload it to Imgur or Dropbox
        int questionIndex = url.indexOf("?");
        url = url.substring(0, questionIndex);

        if (Image.isFileImageExtension(url))
        {
            Imgur imgur = new Imgur();
            newUrl = imgur.uploadImage(url, Imgur.ALBUM_ID_TRACKDUCK_REPORTS, "url");
        }
        else
        {
            Dropbox dropbox = new Dropbox();
            newUrl = dropbox.uploadReportFile(url);
        }
        return newUrl;
    }
}
