package trackducktests;

import dropbox.Dropbox;
import org.testng.annotations.Test;

public class DropboxTests
{
    @Test
    public void uploadNewFile() throws Exception
    {
        String originalUrl = "https://www.google.co.uk/robots.txt";
        Dropbox dropbox = new Dropbox();
        String dropboxUrl = dropbox.uploadReportFile(originalUrl);
        System.out.println(dropboxUrl);
    }
}
