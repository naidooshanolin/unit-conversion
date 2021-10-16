# Unit Conversion Assessment
Below you will find:
* Original instructions sent to me via mail
* Scope and assumptions I made based off the instructions recieved
* Some Dev notes
* How to start the application in docker

# The original instructions: 
1. Create a Spring Boot application using rest API’s to do a conversions.
2. Convert Metric to imperial and vice versa. The API must cater for at least 5 conversions (include temperature conversion please).
3. Ensure that you have proper Unit and Integration testing that accompany the project.
4. Postman/Jmeter project with your RestAPI's.
5. Upload your project into your own GIT repository and share the project with us.
6. The project must be able to run in its own Docker environment
7. Build system must use Maven.

# Scope and assumptions
* 4 of the conversions sit in the length category [mm,cm,m,km,inch,feet,yard,mile]. You will be
able to convert between metric and imperial. You can also do conversions within each family.
i.e. you can convert mm to km [metric family] or inch to feet [imperial family]
* the 5th type of conversion happens in the temperature category. You will be able to convert
from celsius to fahrenheit and vice versa.
* There is no security added to the service and therefore also no session management.
* Decimal precision has been set to 10.
* Malformed requests use default Spring MVC error handling.
* Unsupported conversions use a custom error response.
* service can be started from docker.  
* Postman example system level tests. (reluctantly added).
* Complete test coverage at component level.

# Some Dev Notes
This type of service does not really lend itself to System Level Tests in postman. However, I have
added some tests for the happy paths. Component level tests are covering all permutations of
the conversions.

You can find the postman collection and the environment file in /src/test/postman.
Alternatively, you can import it from the github repo via postman using the git import.

Thank you in advance for taking the time to read through all the code I wrote for you.

# How to start the application in docker
Since maven is the build tool of choice, I have chosen to use the out of the box support
Spring Boot and maven has for docker. Here are the commands you can run:

* mvn spring-boot:build-image 
* docker run -p 8080:8080 -t unit-conversion:1.0.0

You may tweak both commands to match your local machine's needs