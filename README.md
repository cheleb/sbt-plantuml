# sbt-plantuml-plugin

## Prerequisite

[Graphviz](https://www.graphviz.org/) must be in the $PATH.

## Installation 

In project/plugins.sbt
```
resolvers += Resolver.bintrayIvyRepo("metabookmarks", "sbt-plugin-releases")

addSbtPlugin("io.metabookmarks" % "sbt-plantuml-plugin" % "0.0.66")

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





