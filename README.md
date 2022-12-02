# SAC
A Tetris implementation

![build](https://github.com/jimmy-zx/SAC/actions/workflows/maven.yml/badge.svg)

## Build

```shell
git clone https://github.com/jimmy-zx/SAC.git
cd SAC
mvn package
```

### Running
```shell
mvn javafx:run
```

or
```shell
java -cp target/SAC-[version]-SNAPSHOT.jar sac.App
```

### Using IntelliJ

1. Open the project
2. Run -> Edit Configurations -> Add New Configuration -> Maven

## Documentation

### Code documentation
To generate code documentation, use
```shell
mvn javadoc:javadoc
```

### Design document

Compile [this document](/docs/design)

### Roadmap

[Roadmap](https://github.com/jimmy-zx/SAC/wiki/Roadmap)

## Troubleshooting

### 1. Running Maven `javafx:run` in IntelliJ uses system default JDK rather than project JDK.

Uncheck `Include system environmental variables` in run configuration.

### 2. Error: JavaFX runtime components are missing, and are required to run this application
This should not happen when using `mvn javafx:run`.
Add the following options to the command line:
```
--module-path "[path to javafx]\lib" --add-modules javafx.controls,javafx.fxml
```

Copyright (c) 2022 Stand Alone Complex
