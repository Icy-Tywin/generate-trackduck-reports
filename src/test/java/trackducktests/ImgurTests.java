package trackducktests;

import imgur.Imgur;
import org.testng.annotations.Test;

public class ImgurTests
{
    @Test
    public void tryNewUploadUrl() throws Exception
    {
        Imgur imgur = new Imgur();
        String filePath = "https://upload.wikimedia.org/wikipedia/commons/3/37/African_Bush_Elephant.jpg";
        String link = imgur.uploadImage(filePath, imgur.ALBUM_ID_TRACKDUCK_REPORTS, "url");
        System.out.println(link);
    }
}
