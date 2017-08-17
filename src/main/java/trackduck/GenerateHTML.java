package trackduck;

import j2html.tags.ContainerTag;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class GenerateHTML
{
    String PROJECTNAME = GenerateData.PROJECTNAME;
    String PROJECTSTARTDATE = GenerateData.PROJECTSTARTDATE;
    String PROJECTENDDATE = GenerateData.PROJECTENDDATE;
    String REPORTPATH = GenerateData.REPORTPATH;
    String ISSUEIMAGEIDPREFIX = "issimg";
    String ISSUECOMMENTSIDPREFIX = "isscomm";

    @Test
    public void generateHTML(List<Issue> issues, String projectName)
    {
        String date = new SimpleDateFormat("dd/MM/yyy").format(new Date());
        String fullDate = new SimpleDateFormat("yyyMMddhhmmss").format(new Date());
        String HTMLFILEPATH = REPORTPATH + projectName + "_" + fullDate + ".html";
        //List<Issue> issues = testListIssues();

        String html =
                document().render() +
                        html().with(
                                //HEAD//
                                tdHead(),

                                //BODY
                                body().attr("style", "margin:4px; padding:4px").with(
                                        h1("Defect Report for Project " + projectName),
                                        h4().with(
                                                span().withClass("glyphicon glyphicon-calendar"),
                                                text(" Report generated on " + date)
                                        ),
                                        h4().with(
                                                span().withClass("glyphicon glyphicon-resize-horizontal"),
                                                text(" UAT duration " + PROJECTSTARTDATE + " - " + PROJECTENDDATE)
                                        ),

                                        //STATISTICS
                                        tdStatistics(issues),

                                        //MAIN CONTENT
                                        div().withClass("container-fluid").with(
                                                div().withClass("row").with(
                                                        div().withClass("col-md-12").attr("style", "min-width: 600px").with(
                                                                table().withClass("table table-bordered table-hover").with(
                                                                        //TABLE HEADERS
                                                                        thead().with(
                                                                                tr().with(
                                                                                        th("Issue"),
                                                                                        th("Detail"),
                                                                                        th("Comments")
                                                                                )
                                                                        ),
                                                                        tbody().with(
                                                                                issues.stream().map(issue ->
                                                                                        tr().with(
                                                                                                //ISSUE DESCRIPTION
                                                                                                td().withClass("col-md-4").with(

                                                                                                        tdImageButton(true, ISSUEIMAGEIDPREFIX + issue.Id),
                                                                                                        tdImageModal(ISSUEIMAGEIDPREFIX + issue.Id, issue.Screenshot),
                                                                                                        (!issue.getClosed())
                                                                                                                ? span().withClass("glyphicon glyphicon-eye-open").attr("style", "padding-left:5px")
                                                                                                                : span().attr("style", "display: none"),
                                                                                                        (issue.getPriority())
                                                                                                                ? span().withClass("glyphicon glyphicon-star").attr("style", "padding-left:5px")
                                                                                                                : span().attr("style", "display: none"),
                                                                                                        //tdShowPriorityIcon(issue),
                                                                                                        //).condWith(issue.getPriority(), span().withClass("")),
                                                                                                        text(" #" + issue.Id + " - " + issue.Description),
                                                                                                        br(), br(),
                                                                                                        //ISSUE ATTACHMENT
                                                                                                        div().with(
                                                                                                                issue.IssueAttachments.stream().map(issueAttachment ->
                                                                                                                        tdIssueAttachment(issueAttachment)
                                                                                                                ).collect(Collectors.toList())
                                                                                                        )
                                                                                                ),
                                                                                                //ISSUE DETAILS
                                                                                                tdIssueDetail(issue),
                                                                                                //ISSUE COMMENTS
                                                                                                td().withClass("col-md-4").attr("style", "min-width: 100px").with(
                                                                                                        a().withHref("javascript:;").withClass("list-group-item").attr("data-toggle", "collapse").attr("data-target", "#" + ISSUECOMMENTSIDPREFIX + issue.Id).attr("data-parent", "#menu").with(
                                                                                                                span().withClass("glyphicon glyphicon-plus-sign"),
                                                                                                                text(" COMMENTS "),
                                                                                                                span().withClass("label label-info").with(
                                                                                                                        text(Integer.toString(issue.IssueComments.size()))
                                                                                                                ),
                                                                                                                span().withClass("glyphicon glyphicon-comment pull-right")
                                                                                                        ),
                                                                                                        div().withId(ISSUECOMMENTSIDPREFIX + issue.Id).withClass("sublinks collapse").with(
                                                                                                                issue.IssueComments.stream().map(issueComment ->
                                                                                                                        tdIssueComment(issueComment)
                                                                                                                ).collect(Collectors.toList())
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ).collect(Collectors.toList())
                                                                        )

                                                                )
                                                        )
                                                )
                                        )
                                )
                        ).render();

        System.out.println(html);

        try
        {
            PrintWriter out = new PrintWriter(new File(HTMLFILEPATH));
            out.println(html);
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public ContainerTag tdHead()
    {
        ContainerTag html;

        html = head().with(
                title("Defect Report for Project " + PROJECTNAME),
                meta().withCharset("UTF-8"),
                meta().withName("viewport").withContent("width=device-width, initial-scale=1.0"),
                link().withRel("stylesheet").withHref("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"),
                script().withSrc("https://code.jquery.com/jquery-1.12.0.min.js"),
                script().withSrc("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js")
        );

        //System.out.println(html);

        return html;
    }

    public ContainerTag tdStatistics(List<Issue> issues)
    {
        int noOfOpenIssues = 0;
        int noOfPriorityIssues = 0;

        for (Issue issue : issues)
        {
            if (issue.getClosed() == false)
                noOfOpenIssues++;
            if (issue.getPriority() == true)
                noOfPriorityIssues++;
        }

        ContainerTag html;
        //STATISTICS
        html = table().withClass("table").with(
                tbody().with(
                        tr().with(
                                td().attr("style", "vertical-align:middle;").withClass("text-center").with(
                                        span().withClass("glyphicon glyphicon-stats")
                                ),
                                td().with(
                                        h3("Total number of issues ").with(
                                                span().withClass("label label-primary").with(
                                                        text(Integer.toString(issues.size()))
                                                )

                                        ),
                                        h5("Total number of open issues ").with(
                                                span().withClass("label label-warning").with(
                                                        text(Integer.toString(noOfOpenIssues))
                                                )

                                        ),
                                        h5("Total number of priority issues ").with(
                                                span().withClass("label label-danger").with(
                                                        text(Integer.toString(noOfPriorityIssues))
                                                )

                                        )
                                )
                        )
                )
        );
        return html;
    }

    public ContainerTag tdImageButton(Boolean isIssueAttachment, String imageId)
    {
        String buttonClass = "";
        if (isIssueAttachment)
            buttonClass = "btn btn-success";
        else
            buttonClass = "btn btn-info";

        ContainerTag html =
                button().withType("button").withClass(buttonClass).attr("data-toggle", "modal").attr("data-target", "#" + imageId).with(
                        span().withClass("glyphicon glyphicon-picture")
                );
        return html;
    }

    public ContainerTag tdImageModal(String imageId, String imageUrl)
    {
        ContainerTag html =
                div().withId(imageId).withClass("modal fade").attr("tabindex", "-1").attr("role", "dialog").attr("aria-labelledby", "myModalLabel").attr("aria-hidden", "true").with(
                        div().withClass("modal-dialog").with(
                                div().withClass("modal-content").with(
                                        div().withClass("modal-body").with(
                                                img().withSrc(imageUrl).withClass("img-responsive")
                                        )
                                )
                        )
                );


        return html;
    }

    public ContainerTag tdShowPriorityIcon(Issue issue)
    {
        ContainerTag html = null;
        if (issue.getPriority())
        {
            html = span().withClass("glyphicon glyphicon-star");
        }
        return html;
    }


    public ContainerTag tdIssueAttachment(IssueAttachment attachment)
    {
        String id = RandomStringUtils.randomAlphanumeric(8).toUpperCase();

        Boolean isImage = isImage(attachment.Url);

        ContainerTag html = null;

        if (isImage)
        {
            html =
                    p().with(
                            span().withClass("glyphicon glyphicon-paperclip"),
                            text("  " + attachment.Text + "  "),
                            button().withType("button").withClass("btn btn-info").attr("data-toggle", "modal").attr("data-target", "#" + id).with(
                                    span().withClass("glyphicon glyphicon-picture")
                            ),
                            div().withId(id).withClass("modal fade").attr("tabindex", "-1").attr("role", "dialog").attr("aria-labelledby", "myModalLabel").attr("aria-hidden", "true").with(
                                    div().withClass("modal-dialog").with(
                                            div().withClass("modal-content").with(
                                                    div().withClass("modal-body").with(
                                                            img().withSrc(attachment.Url).withClass("img-responsive")
                                                    )
                                            )
                                    )
                            )
                    );
        }
        else
        {
            html =
                    p().with(
                            span().withClass("glyphicon glyphicon-paperclip"),
                            text(" " + attachment.Text + " "),
                            a().withHref(attachment.Url).withTarget("_blank").withClass("btn btn-info").with(
                                    span().withClass("glyphicon glyphicon-file")
                            )
                    );
        }

        return html;
    }


    public ContainerTag tdIssueDetail(Issue issue)
    {
        String detailIcon = "glyphicon glyphicon-info-sign";
        String id = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        String openClosed = "Open";
        String priority = "low";
        if (issue.Closed == true)
            openClosed = "Closed";
        if (issue.Priority == true)
            priority = "high";


        ContainerTag html =
                td().withClass("col-md-4").attr("style", "min-width: 100px").with(
                        a().withHref("javascript:;").withClass("list-group-item").attr("data-toggle", "collapse").attr("data-target", "#" + id).attr("data-parent", "panel-340460").with(
                                span().withClass("glyphicon glyphicon-plus-sign "),
                                text(" DETAIL "),
                                span().withClass(detailIcon + " pull-right")
                        ),
                        div().withId(id).withClass("sublinks collapse").with(
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-calendar"),
                                        text(" Date: " + issue.Date)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-user"),
                                        text(" Author: " + issue.Author)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-link"),
                                        text(" Source: " + issue.Source)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-globe"),
                                        text(" Browser: " + issue.Browser)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-hdd"),
                                        text(" OS: " + issue.OperatingSystem)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-eye-open"),
                                        text(" " + openClosed)
                                ),
                                a().withClass("list-group-item small").with(
                                        span().withClass("glyphicon glyphicon-asterisk"),
                                        text(" Priority: " + priority)
                                )
                        )
                );

        return html;
    }

    public ContainerTag tdIssueComment(IssueComment comment)
    {
        ContainerTag html =
                ul().withClass("list-group-item small").with(
                        p().with(
                                span().withClass("glyphicon glyphicon-user"),
                                text("  " + comment.Author)
                        ),
                        p().with(
                                span().withClass("glyphicon glyphicon-calendar"),
                                text("  " + comment.Date)
                        ),
                        span().withClass("glyphicon glyphicon-pencil"),
                        text(" " + comment.Text),
                        p().with(
                                comment.Attachments.stream().map(attachment ->
                                        tdCommentAttachment(attachment)
                                ).collect(Collectors.toList())
                        )


                );

        return html;
    }

    public ContainerTag tdCommentAttachment(CommentAttachment attachment)
    {
        Boolean isImage = isImage(attachment.Url);

        String id = RandomStringUtils.randomAlphanumeric(8).toUpperCase();

        ContainerTag html = null;

        if (isImage)
        {
            html =
                    p().with(
                            span().withClass("glyphicon glyphicon-paperclip"),
                            text("  " + attachment.Text + " "),
                            button().withType("button").withClass("btn btn-info").attr("data-toggle", "modal").attr("data-target", "#" + id).with(
                                    span().withClass("glyphicon glyphicon-picture")
                            ),
                            div().withId(id).withClass("modal fade").attr("tabindex", "-1").attr("role", "dialog").attr("aria-labelledby", "myModalLabel").attr("aria-hidden", "true").with(
                                    div().withClass("modal-dialog").with(
                                            div().withClass("modal-content").with(
                                                    div().withClass("modal-body").with(
                                                            img().withSrc(attachment.Url).withClass("img-responsive")
                                                    )
                                            )
                                    )
                            )
                    );
        }
        else
        {
            html =
                    p().with(
                            span().withClass("glyphicon glyphicon-paperclip"),
                            text(" " + attachment.Text + " "),
                            a().withHref(attachment.Url).withTarget("_blank").withClass("btn btn-info").with(
                                    span().withClass("glyphicon glyphicon-file")
                            )
                    );
        }
        return html;
    }

    public Boolean isImage(String url)
    {
        url = url.toLowerCase();
        Boolean isImage = false;
        if (url.contains("imgur"))
            isImage = true;
        return isImage;
    }

}
