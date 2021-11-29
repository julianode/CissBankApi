# Ciss Bank Basic REST Application
This is a test for a job interview.

I uploaded the "build" folder on purpose in order to help with visualizing the API documentation and running the API easily. 

### How to run this application without building it
Inside this project root folder (basiccissbankapi), open your terminal, and run:
* java -jar build/libs/basiccissbankapi-0.0.1-SNAPSHOT.jar

### How to run this application building it
Inside this project root folder (basiccissbankapi), open your terminal, and run:
* gradle build

You may need to wait some downloads and indexing. Then run:
* gradle bootRun

### Reference Documentation
Please check these folders:

* src/main/resources

Here you may find the scope of this test, the BACEN Rules this application was based upon and a QA test folder.

* src/main/resources/QA test collection with Postman and IntelliJ

This folder contains all the Postman collection needed to test the application.
Please, follow its calls sequence from top to bottom (Auth to Delete).
Remember to create and approve at least two accounts.

Furthermore, there is a screenshot of how to configure you IntelliJ if you want to debug the application while using Postman.

Just add that configuration and hit the debug button, then spread some breakpoints, and finally start playing with Postman calls.

When using Postman, always start with the "healthCheck" call, because it authenticates you.

### JAVADOCS
You may find the javadocs of this application at:
* build/docs/index.html

Just use your browser to navigate.




