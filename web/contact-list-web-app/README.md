My Contact List Web Application Notes

# Design principles

* Turn static HTML page into dynamic page by using **client-side scripting**. No JSP, ASP, PHP and similar server-side technologies.
* Use HTML, JavaScript and CSS **directly**. No using one language to generate code in another language.
* Use **libraries** (JQuery) but **not frameworks** (AngularJS, Backbone, Ember).
* Implement complete **separation of presentation from logic**, by using JavaScript to manipulate **standard HTML page**.
  [No templates](http://www.workingsoftware.com.au/page/Your_templating_engine_sucks_and_everything_you_have_ever_written_is_spaghetti_code_yes_you),
  template engines and template language.

# Simple deployment

1. Copy 4 files from **src/main/webapp** to a folder somewhere on your desktop:
   * index.html
   * styles.css
   * controller.js
   * persistence.js
2. Make sure the head section of **index.html** has a reference to**persistence.js**, and not persistence-rest.js
3. Open **index.html** in your web browser

# WebApp deployment

1. Make sure the head section of **index.html** has a reference to **persistence-rest.js**, and not persistence.js
2. Run `mvn clean install`. This will build the WAR file and also download H2 jar to local maven repository.
3. Start H2 database, by running the following command (replace **/var/opt/h2** with a folder on your machine):<br>
  `java -cp  ~/.m2/repository/com/h2database/h2/1.4.190/h2-1.4.190.jar org.h2.tools.Server -tcp -web -baseDir /var/opt/h2`
4. Open <a href="http://localhost:8082">H2 console</a> and login using the url, user-name and password found in **src/main/webapp/META-INF/context.xml**
5. Create contacts table with the script found in **src/main/resources/create-database-tables.sql**
6. Start Apache Tomcat. [Download](http://tomcat.apache.org) and install it first, if necessary.
7. Deploy **target/contact-list-web-app.war** to Apache Tomcat.


