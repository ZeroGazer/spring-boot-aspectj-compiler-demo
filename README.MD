This demo aims to integrate spring boot, aspectj compile-time weaving and lombok.

---
###Maven

Dependency
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>${aspectj.version}</version>
</dependency>
```

Since Mojo's aspectj-maven-plugin does not support JDK version > 8, a forked plugin is used
```xml
<plugin>
    <groupId>com.nickwongdev</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>${aspectj-maven-plugin.version}</version>
    <configuration>
        <complianceLevel>${java.version}</complianceLevel>
        <source>${java.version}</source>
        <target>${java.version}</target>
        <showWeaveInfo>true</showWeaveInfo>
        <verbose>true</verbose>
        <Xlint>ignore</Xlint>
        <encoding>UTF-8</encoding>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
            </aspectLibrary>
        </aspectLibraries>
        <weaveDirectories>
            <weaveDirectory>${project.build.directory}/classes</weaveDirectory>
        </weaveDirectories>
        <forceAjcCompile>true</forceAjcCompile>
        <sources/>
    </configuration>
    <executions>
        <execution>
            <phase>process-classes</phase>
            <goals>
                <goal>compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

The plugin should be executed after all classes compiled by javac with lombok
```xml
<execution>
    <phase>process-classes</phase>
    <goals>
        <goal>compile</goal>
    </goals>
</execution>
```

Force ajc to process main classes
```xml
<configuration>
  <weaveDirectories>
        <weaveDirectory>${project.build.directory}/classes</weaveDirectory>
    </weaveDirectories>
    <forceAjcCompile>true</forceAjcCompile>
    <sources/>
</configuration>
```
---
###@Cacheable

Use AdviceMode.ASPECTJ instead of AdviceMode.PROXY in @EnableCaching
```
@EnableCaching(mode = AdviceMode.ASPECTJ)
```

---

###@Transactional

Use AdviceMode.ASPECTJ instead of AdviceMode.PROXY in @EnableTransactionManagement
```
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
```

---

Generally, AdviceMode.ASPECTJ should be applied to Spring's annotations in order to enable AspectJ weaving. For example:
```
@EnableAsync(mode = AdviceMode.ASPECTJ)
@EnableGlobalMethodSecurity(mode = AdviceMode.ASPECTJ)
```
