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

To use Imgur and Dropbox you will need to create a file called appsettings.xml in C:\Automation\Settings. On the to do list for this project is to make that path configurable. The xml file should have this structure

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?><settings>
	<Imgur>
		<client_id>IMGUR_CLIENTID</client_id>
		<client_secret>IMGUR_CLIENTSECRET</client_secret>
		<token>IMGUR_TOKEN</token>
		<refresh_token>IMGUR_REFRESHTOKEN</refresh_token>
		<album_id_trackduck_reports>IMGUR_ALBUM_ID</album_id_trackduck_reports>
	</Imgur>	
	<Dropbox>		
		<access_token>DROPBOX_ACCESSTOKEN</access_token>
	</Dropbox>
	<Trackduck>
    		<username>TRACKDUCK_USERNAME</username>
    		<password>TRACKDUCK_PASSWORD</password>
    		<project_url>TRACKDUCK_PROJECTURL</project_url>
    		<project_name>Client name - Code - Project name</project_name>
    		<project_start_date>TRACKDUCK_PROJECT_START</project_start_date>
    		<project_end_date>TRACKDUCK_PROJECT_END</project_end_date>
    	</Trackduck>
</settings>
```