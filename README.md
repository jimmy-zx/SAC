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

## Roadmap

### 0.0
A Tetris prototype
#### 0.0.1 (current)
Project design document
#### 0.0.2
A playable instance of Tetris

- [x] Tetris Piece
- [x] Tetris Board
- [ ] Tetris Model
- [ ] Tetris Runner
- [ ] Tetris View
### 0.1
Implement an extensible API
### 0.2
Implement most of the [Tetris Guideline](https://tetris.wiki/Tetris_Guideline)
### 0.3
Support for additional features


## Documentation

[Design document](/docs/design)

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
