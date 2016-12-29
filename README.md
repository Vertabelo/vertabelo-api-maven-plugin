# vertabelo-api-maven-plugin
A maven plugin to download xml and sql from Vertabelo

## How to
Add it to your pom.xml
```XML
 <build>
        <plugins>
          <plugin>
            <groupId>com.vertabelo.maven</groupId>
            <artifactId>vertabelo-maven-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
                <apiToken>${env.VERTABELO_API_TOKEN}</apiToken>
                <modelId>modelId</modelId>
                <destSQLFile>model.sql</destSQLFile>
                <destXMLFile>model.xml</destXMLFile>
            </configuration>
          </plugin>
        </plugins>
    </build>
```

Parameters description:

1. destSQLFILE - destination where sql file will be written
2. destXMLFile - destination where xml file will be written
3. apiToken - API token generated in Vertabelo, more here: http://www.vertabelo.com/blog/documentation/vertabelo-api
4. modelId = model's gid, more here: http://www.vertabelo.com/blog/documentation/vertabelo-api
5. modelTag = model's tag, more here: http://www.vertabelo.com/blog/documentation/vertabelo-api

### Tasks:
```
 mvn com.vertabelo.maven:vertabelo-maven-plugin:vertabeloSQL
```
```
mvn com.vertabelo.maven:vertabelo-maven-plugin:vertabeloXML
```
