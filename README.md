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
mvn clean javafx:run
```

or
```shell
java -cp target/SAC-[version]-SNAPSHOT.jar sac.App
```

### Using IntelliJ

1. Open the project
2. Run -> Edit Configurations -> Add New Configuration -> Maven
3. Change `Command Line` to `package`

## Documentation

[Design document](/docs/design)

Copyright (c) 2022 Stand Alone Complex
