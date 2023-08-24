# Documentation

### Adding module

Naming convention:
1. service - add prefix **service**
2. library - add prefix **lib**

---

#### Adding service module

1. Create a folder inside `modules` according to naming-convention i.e. `SERVICE_NAME-service`
2. Create maven module `api` - comprises controllers (api) for that service.
3. Create maven module `impl` - comprises implementation for api (what api uses, call, etc...)
4. Emphasize parent module for `api` & `impl` modules
    ```xml
    <parent>
        <groupId>com.innowise</groupId>
        <artifactId>educationalsystem</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../../../pom.xml</relativePath>
    </parent>
    ```
5. Add new `api` & `impl` modules to parent pom.xml section `<modules></modules>` that is situated in root folder. 
    ```xml
      <modules>
        <!--...-->
        <module>modules/SERVICE_NAME-service/api</module>
        <module>modules/SERVICE_NAME-service/impl</module>
        <!--...-->
      </modules>
    ```
6. Delete `.gitinnore`, `HELP.md`, `mvnw`, `mvnw.cmd` if present
7. Add `impl` as a dependency to `api` pom.xml module
8. Add `api` module as a dependency to `educationalapplication` pom.xml module as a dependency
---

#### Adding library

1. Create maven module inside `modules` according to naming-convention i.e. `LIBRARY_NAME-lib`
2. place all source code in `src/main/java`
3. Emphasize parent module for `LIBRARY-NAME-lib` module
    ```xml
        <parent>
            <groupId>com.innowise</groupId>
            <artifactId>educationalsystem</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <relativePath>../../pom.xml</relativePath>
        </parent>
    ```
4. Add new `LIBRARY-NAME-lib` module to parent pom.xml section `<modules></modules>` that is situated in root folder.
   ```xml
       <modules>
           <!--...-->
           <module>modules/SERVICE_NAME-service/api</module>
           <module>modules/SERVICE_NAME-service/impl</module>
           <!--...-->
       </modules>
   ```
5. Delete `.gitinnore`, `HELP.md`, `mvnw`, `mvnw.cmd` if present
6. Add `LIBRARY-NAME-lib` module to `educationalapplication` pom.xml module as a dependency