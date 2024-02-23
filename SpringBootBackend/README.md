# Backend README

## System Dependencies
- using Spring Boot 3.2.3
- Maven 3.9.1
- JDK 17

## Project Dependencies 
All are included in ```pom.xml``` but listed here for convenience:
- Spring Web
- Spring Data JPA
- MySQL Driver
- Lombok
    - Convenient tool for code-cleanliness (Ex: can annotate a class with ```@AllArgsConstructor``` to create a constructor not visible in the code)

**IMPORTANT**
- You will need to add a file called ```application.properties``` that will be configured with your specific MySQL credentials and port numbers.
- The full file path should be ```src/main/resources/application.properties```
- See the template ```src/main/resources/application.properties-template.txt```
- You should be able to push and pull while leaving your credentials in the ```application.properties``` file. The .gitignore file should handle this.