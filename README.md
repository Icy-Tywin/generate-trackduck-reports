# generate-trackduck-reports

A tool to generate detailed HTML bug reports for Trackduck projects - https://app.trackduck.com

I use Trackduck for work item tracking during user acceptance testing. I had a requirement to provide bug reports at the end of UAT, however Trackduck doesn't have detailed report generation built in. 

This tool uses Selenium webdriver to login to Trackduck, click through each task in a project and store information about issues, attachments, comments and comment attachments.

Any images are uploaded to Imgur using v3 of the [Imgur API](https://apidocs.imgur.com/) and the direct link stored. 

Any other files are uploaded to Dropbox using the [Dropbox SDK](https://www.dropbox.com/developers/documentation/java) and the share link stored.

[J2html](https://j2html.com/) is used to generate an html report from all this information.

The HTML reports use [Bootstrap](http://getbootstrap.com/) styles

An example of a generated report can be viewed [here](http://keylimecode.com/wp-content/uploads/2017/08/0516-DCU-The-Helix_20170208033308.html)

![Trackduck HTML Report](http://i.imgur.com/tPgUv37.png)