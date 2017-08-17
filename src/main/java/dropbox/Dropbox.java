package dropbox;

import business.Common;
import business.Image;
import business.Settings;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import java.io.FileInputStream;
import java.io.InputStream;

public class Dropbox
{
    private static final String ACCESS_TOKEN = Settings.read("//Dropbox//access_token");
    public final String TEMP_LOCAL_FOLDER = Common.resourcePathFolder + "\\DropboxTemp\\";
    public final String REPORT_FOLDER = "/TrackDuckReports";

    public DbxClientV2 createClient()
    {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }

    public String createReportDateTimeFolder()
    {
        String newFolderPath = "";
        try
        {
            DbxClientV2 client = createClient();
            newFolderPath = REPORT_FOLDER + "/" + Common.dateTimeString();
            FolderMetadata folderMetadata = client.files().createFolder(newFolderPath);
            newFolderPath = folderMetadata.getPathLower();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return newFolderPath;
    }

    public String uploadReportFile(String pFilePath)
    {
        String uploadFolder = createReportDateTimeFolder();
        String url = uploadFile(pFilePath, uploadFolder);
        return url;
    }

    public String uploadFile(String pFilePath, String pUploadFolder)
    {

        String url = "";
        FileMetadata metadata = null;
        String title = pFilePath.substring(pFilePath.lastIndexOf("/") + 1);
        try
        {
            if (pFilePath.contains("http"))
            {
                String destinationFolder = Common.createFolder(TEMP_LOCAL_FOLDER + Common.dateString());
                String destinationFile = destinationFolder + "\\" + title;
                pFilePath = Image.saveFileFromUrl(pFilePath, destinationFile);
                title = pFilePath.substring(pFilePath.lastIndexOf("/") + 1);
                title = pFilePath.substring(pFilePath.lastIndexOf("\\") + 1);
            }

            DbxClientV2 client = createClient();
            try (InputStream in = new FileInputStream(pFilePath))
            {
                String uploadFilePath = pUploadFolder + "/" + title;
                metadata = client.files().uploadBuilder(uploadFilePath).uploadAndFinish(in);
                SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(metadata.getPathLower());
                if (Image.isFileImage(pFilePath))
                    url = getDirectLink(sharedLinkMetadata.getUrl());
                else
                    url = sharedLinkMetadata.getUrl();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return url;
    }

    public String getDirectLink(String originalLink)
    {
        String newLink = originalLink.substring(0, originalLink.length() - 5);
        newLink = newLink + "?raw=1";
        return newLink;
    }

}
