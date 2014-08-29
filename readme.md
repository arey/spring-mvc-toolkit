# Spring MVC Toolkit #

This toolkit provides additional features to the Spring MVC module of the Spring Framework.

## Features ##

* Input tag supporting HTML 5 form validation by using Bean Validation constraints

## Documentation ##


**Html5InputTag**

This JSP tag adds HTML 5 form validation to the default Spring MVC input tag by using Bean Validation constraints.
It supports:
* @Max, @Min, @NotNull @Size annotations form the Bean Validation API.
* Custom @Email, @NotEmpty and @URL annotations from the Hibernate Validator implementation

Here the mapping between Java and HTML 5 code:
<table>
	<thead>
		<tr>
			<td>Java Code</td>
			<td>JSP Page</td>
			<td>Generated HTML 5</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>@NotEmpty<br>String firstName;</td>
			<td>&lt;jem:input path=&quot;firstName&quot; /&gt;</td>
			<td>&lt;input id=&quot;firstName&quot; type=&quot;text&quot; required=&quot;required&quot; /&gt;</td>
		</tr>
		<tr>
			<td>@NotNull<br>String city;</td>
			<td>&lt;jem:input path=&quot;city&quot; /&gt;</td>
			<td>&lt;input id=&quot;city&quot; type=&quot;text&quot; required=&quot;required&quot; /&gt;</td>
		</tr>		
		<tr>
			<td>@Size(max=40)<br>String address;</td>
			<td>&lt;jem:input path=&quot;address&quot; /&gt;</td>
			<td>&lt;input id=&quot;address&quot; type=&quot;text&quot; maxlength=&quot;40&quot; /&gt;</td>
		</tr>	
		<tr>
			<td>@Size(max=40)<br>String address;</td>
			<td>&lt;jem:input path=&quot;address&quot; maxlength=&quot;20&quot; /&gt;</td>
			<td>&lt;input id=&quot;address&quot; type=&quot;text&quot; maxlength=&quot;20&quot; /&gt;</td>
		</tr>	
		<tr>
			<td>@Min(value=18)<br>@Max(value=99)<br>Integer age;</td>
			<td>&lt;jem:input path=&quot;age&quot; /&gt;</td>
			<td>&lt;input id=&quot;age&quot; type=&quot;number&quot; min=&quot;18&quot; max=&quot;99&quot; /&gt;</td>
		</tr>							
		<tr>
			<td>@Email<br>String email;</td>
			<td>&lt;jem:input path=&quot;email&quot; /&gt;</td>
			<td>&lt;input id=&quot;email&quot; type=&quot;email&quot; /&gt;</td>
		</tr>
		<tr>
			<td>@URL<br>String website;</td>
			<td>&lt;jem:input path=&quot;website&quot; /&gt;</td>
			<td>&lt;input id=&quot;website&quot; type=&quot;url&quot; /&gt;</td>
		</tr>
		<tr>
			<td>Integer birthYear;</td>
			<td>&lt;jem:input path=&quot;birthYear&quot; /&gt;</td>
			<td>&lt;input id=&quot;birthYear&quot; type=&quot;number&quot; /&gt;</td>
		</tr>		
	</tbody>
</table>


## Quick Start ##

Download the jar though Maven:

```xml
<dependency>
  <groupId>com.javaetmoi.core</groupId>
  <artifactId>spring-mvc-toolkit</artifactId>
  <version>0.1.0</version>
</dependency> 
       
<repository>
  <id>javaetmoi-maven-release</id>
  <releases>
    <enabled>true</enabled>
  </releases>
  <name>Java et Moi Maven RELEASE Repository</name>
  <url>http://repository-javaetmoi.forge.cloudbees.com/release/</url>
</repository>
```


## Demo ##

Download the code with git:
```
git clone git://github.com/arey/spring-mvc-toolkit.git
```

Compile the code with maven:
```
cd spring-mvc-tookit
mvn clean install
```

Run jetty
```
cd spring-mvc-toolkit-demo
mvn jetty:run-war
```

Open your browser and goto:
* http://localhost:8080/htmlvalidation for demo of the Html5InputTag


## Contributing to Spring MVC Tookit ##

* Github is for social coding platform: if you want to write code, we encourage contributions through pull requests from [forks of this repository](http://help.github.com/forking/). If you want to contribute code this way, please reference a GitHub ticket as well covering the specific issue you are addressing.

### Development environment installation ###

Download the code with git:
git clone git://github.com/arey/spring-mvc-toolkit.git

Compile the code with maven:
mvn clean install

If you're using an IDE that supports Maven-based projects (InteliJ Idea, Netbeans or m2Eclipse), you can import the project directly from its POM. 
Otherwise, generate IDE metadata with the related IDE maven plugin:
mvn eclipse:clean eclipse:eclipse


## Release Note ##

<table>
  <tr>
    <th>Version</th><th>Release date</th><th>Features</th>
  </tr>
<tr>
    <td>0.2.0-SNAPSHOT</td><td>next version</td><td></td>
  </tr>
  <tr>
    <td>0.1.0</td><td>29/08/2014</td><td>Html5InputTag for HTML 5 validation from Bean Validation constraints</td>
  </tr>
</table>

## Credits ##

* Uses [Maven](http://maven.apache.org/) as a build tool
* Uses [Cloudbees](http://www.cloudbees.com/foss) and [Travis CI](www.travis-ci.org) for continuous integration builds whenever code is pushed into GitHub

## Build Status ##

Travis : [![Build
Status](https://travis-ci.org/arey/spring-mvc-toolkit.png?branch=master)](https://travis-ci.org/arey/spring-mvc-toolkit)

Cloudbees Jenkins : [![Build
Status](https://javaetmoi.ci.cloudbees.com/job/spring-mvc-toolkit/badge/icon)](https://javaetmoi.ci.cloudbees.com/job/spring-mvc-toolkit/)
