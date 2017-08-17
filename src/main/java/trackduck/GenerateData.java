package trackduck;

import org.openqa.selenium.WebDriver;

public class GenerateData
{
    // TODO - read these from appsettings.xml
    //===CONFIGURE THESE SETTINGS FOR EACH PROJECT===
    public String PROJECTURL = "https://app.trackduck.com/project/59358e8a834edffb458ba89d/issue";
    public static String PROJECTNAME = "Translink - 0545 - Research & Recommendations";
    public static String PROJECTSTARTDATE = "05/06/2017";
    public static String PROJECTENDDATE = "23/06/2017";
    //===================

    public static WebDriver driver;
    public static String REPORTPATH = "C:/Automation/TrackDuck/GeneratedReports/";
}
