package trackducktests;

import trackduck.CommentAttachment;
import trackduck.Issue;
import trackduck.IssueAttachment;
import trackduck.IssueComment;

import java.util.ArrayList;
import java.util.List;

public class IssuesTests
{
    //region TESTS

    public List<Issue> testListIssues()
    {
        List<Issue> issues = new ArrayList<>(2);

        //ISSUE ONE
        Issue issueOne = new Issue();
        issueOne.Id = "1";
        issueOne.Description = "Issue One description";
        issueOne.Screenshot = "http://i.imgur.com/8dDAgCC.jpg";
        issueOne.Date = "23 June";
        issueOne.Author = "Issue One author";
        issueOne.Source = "http://www.google.co.uk";
        issueOne.Browser = "Issue One browser";
        issueOne.OperatingSystem = "Issue One OS";
        issueOne.Closed = true;
        issueOne.Priority = false;

        IssueAttachment iOneAttachOne = new IssueAttachment();
        iOneAttachOne.Url = "http://i.imgur.com/vaAaeWf.jpg";
        iOneAttachOne.Text = "Issue 1 Attach 1 Text";
        issueOne.IssueAttachments.add(iOneAttachOne);

        IssueAttachment iOneAttachTwo = new IssueAttachment();
        iOneAttachTwo.Url = "https://www.dropbox.com/s/uu78hy5h8ydt1cx/Test_5.csv?dl=0";
        iOneAttachTwo.Text = "Issue 1 Attach 2 Text";
        issueOne.IssueAttachments.add(iOneAttachTwo);

        IssueComment iOneCommentOne = new IssueComment();
        iOneCommentOne.Author = "Issue1 Comment1 Author";
        iOneCommentOne.Date = "Issue1 Comment1 Date";
        iOneCommentOne.Text = "Issue1 Comment1 Text";

        //comment attachments
        CommentAttachment iOneCOneAOne = new CommentAttachment();
        iOneCOneAOne.Text = "Issue1 Comment1 Attach 1 Text";
        iOneCOneAOne.Url = "http://i.imgur.com/UUuyYZS.jpg";
        iOneCommentOne.Attachments.add(iOneCOneAOne);

        CommentAttachment iOneCOneATwo = new CommentAttachment();
        iOneCOneATwo.Text = "Issue1 Comment1 Attach 2 Text";
        iOneCOneATwo.Url = "https://www.dropbox.com/s/5rp4uxyr9qom3m0/Test_100.csv?dl=0";
        iOneCommentOne.Attachments.add(iOneCOneATwo);
        issueOne.IssueComments.add(iOneCommentOne);

        issues.add(issueOne);


        //ISSUE TWO
        Issue issueTwo = new Issue();
        issueTwo.Id = "2";
        issueTwo.Description = "Issue Two description";
        issueTwo.Screenshot = "http://i.imgur.com/xIcsMCZ.jpg";
        issueTwo.Date = "23 June";
        issueTwo.Author = "Issue Two author";
        issueTwo.Source = "http://www.i3digital.com";
        issueTwo.Browser = "Issue Two browser";
        issueTwo.OperatingSystem = "Issue Two OS";
        issueTwo.Closed = false;
        issueTwo.Priority = true;

        IssueComment iTwoCommentOne = new IssueComment();
        iTwoCommentOne.Author = "Issue2 Comment1 Author";
        iTwoCommentOne.Date = "Issue2 Comment1 Date";
        iTwoCommentOne.Text = "Issue2 Comment1 Text";
        issueTwo.IssueComments.add(iTwoCommentOne);

        IssueComment iTwoCommentTwo = new IssueComment();
        iTwoCommentTwo.Author = "Issue2 Comment2 Author";
        iTwoCommentTwo.Date = "Issue2 Comment2 Date";
        iTwoCommentTwo.Text = "Issue2 Comment2 Text";
        issueTwo.IssueComments.add(iTwoCommentTwo);

        issues.add(issueTwo);


        return issues;
    }

    //endregion

}
