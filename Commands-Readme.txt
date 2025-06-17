* to execute main project without test cases

  mvn clean install exec:java -Dexec.mainClass="mainapp.MyApp" -DskipTests=true

* to run test cases

  mvn test
