package trackduck;

import business.Settings;
import org.openqa.selenium.By;

public class ObjRepo
{
    //region Object Repo
    final public static By txtLoginEmail = By.id("id_email");
    final public static By txtLoginPassword = By.id("id_password");
    final public static By btnLoginButton = By.id("submit-auth");

    final public static By btnFilter = By.xpath("//span[@class='icon-filter']");
    final public static By btnShowClosed = By.xpath("//div[text()='Show closed']");

    final public static By btnSortIssues = By.xpath("//li[@class='sorting index']");

    final public static By issueList = By.id("item-list");


    final public static String issueXpath = "(//ul[@id='item-list']//div[@class='listing'])";
    final public static String issueIdXpath = "//span[@class='id ng-binding']";
    final public static By issueDescription = By.xpath("//div[@class='task-description ng-scope']//a[@class='simptip-position-top issue-content-title ng-binding ng-scope editable editable-click']");
    final public static String issueDateXpath = "//span[@class='date ng-binding']";
    final public static By issueScreenshot = By.xpath("//div[@class='issue-screenshot']//img");
    final public static By issueAuthor = By.xpath("(//div[@class='issue-block-c-author']//*[@class='ng-binding'])[1]");
    final public static By issueSource = By.xpath("//div[@class='issue-source']/a");
    final public static By issueBrowser = By.xpath("(//div[@class='issue-details']//span)[2]");
    final public static By issueOS = By.xpath("(//div[@class='issue-details']//span)[6]");
    final public static By issueRes = By.xpath("(//div[@class='issue-details']//span)[10]");
    final public static By issueClosed = By.xpath("//span[@class='flat-checkbox-text ng-binding']");
    final public static By issuePriority = By.xpath("//div[@class='flat-priority-text-cont']/span");

    final public static String commentXpath = "(//ul[@id='issue-comments-list']//li[@class[not(contains(.,'issue-comment-holder ng-scope ng-hide'))]])";
    final public static String commentAuthorXpath = "//span[@class='comment-author ng-binding']";
    final public static String commentDateXpath = "//span[@class='time-ago ng-binding']";
    final public static String commentTextXpath = "//span[@class='q user-comment ng-binding ng-scope']";
    final public static String commentSystemXpath = "//div[@class='system-comments ng-scope']";
    final public static String commentSystemTextXpath = "//span[@class='ng-binding']";

    final public static String commentAttachmentXpath = "//div[@class='comment-attachments']//div[@class='attach-link ng-scope']";
    final public static String commentAttachmentUrlXpath = "//a[1]";

    final public static String attachmentXpath = "(//div[@class='comment-attachments' and @ng-show[contains(.,'issue.attachments.length > 1')]]//div[@class='attach-link ng-scope'])";
    final public static String attachmentUrlXpath = "//a[@class='']";
    final public static String attachmentTextXpath = attachmentUrlXpath + "/span/strong";

    final public static String pagingElementXPath = "//p[@class='ng-binding'][text()='Loading more issues...']";

    final public static String loginEmail = Settings.read("//Trackduck/username");
    final public static String loginPassword = Settings.read("//Trackduck/password");

    //endregion
}
