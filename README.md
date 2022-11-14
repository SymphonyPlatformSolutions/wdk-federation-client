# Symphony Connect Client for WDK

## Contributions
1. Add activities under the [activity](src/main/java/com/symphony/devsol/federation/activity) package.
Use [ListConnectContacts](src/main/java/com/symphony/devsol/federation/activity/ListConnectContacts.java) and
[ListConnectContactsExecutor](src/main/java/com/symphony/devsol/federation/activity/ListConnectContactsExecutor.java) as a reference.
2. SWADL activity names will follow the main activity class name, converted to kebab case. e.g. `ListConnectContacts` -> `list-connect-contacts`. 
Refer to [list-contacts.swadl.yaml](workflows/contacts.swadl.yaml) for an example. 

## Usage
1. Build the project using `./gradlew build`, generating the `lib` directory of JARs
2. Run the WDK bot as usual `java -jar workflow-bot-app.jar`

## Deployment
1. Deploy both the `workflow-bot-app.jar` and the `lib` directory into the same location
2. Run the WDK bot as usual `java -jar workflow-bot-app.jar`

## Spring Notes
- This project uses Spring for reading the common configuration, auto-wiring the FederationClient and employs RestTemplates as a HTTP client
- As the custom activity classes are loaded by WDK, this project's package is not component-scanned by default by Spring
- To enable Spring, the [spring.factories](src/main/resources/META-INF/spring.factories) file is used to define an auto configuration class
- This class - [AutoConfig](src/main/java/com/symphony/devsol/federation/config/AutoConfig.java) - defines the `basePackages` to include in the Spring component scan
- If you wish to modify the base package name of this project, this `basePackages` value will need to be revised as well
