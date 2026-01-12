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

*.puml file under ```src/main/plantuml``` will be processed and *.png file outputed as ```mdoc ``` folder resource.

### Settings

* ```plantUMLSource``` setting to specify the source folder of plantUML files.

* ```plantUMLTarget``` setting to specify the target folder of generated images.

* ```plantUMLFormats``` setting to specify the output formats of generated images.



