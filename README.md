# sbt-plantuml-plugin

## Prerequisite

[Graphviz](https://www.graphviz.org/) must be in the $PATH.

## Installation 

In project/plugins.sbt
```

addSbtPlugin("dev.cheleb" % "sbt-plantuml" % <version>)

```

In build.sbt

```
enablePlugins(PlantUMLPlugin)
```

### Default settings

*.puml file under ```src/main/resources/diagram/``` will be processed and *.png file outputed to ```target/scala-X.XX/resource_managed/main/diagram ```

### Settings

```plantUMLSource```

```plantUMLTarget```





