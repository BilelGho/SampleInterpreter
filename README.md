# Interpreter Project

This project is an interpreter for a custom programming language. The interpreter reads source code written in this language, parses it, and executes the instructions.

## Language Overview

The custom language allows the following features:

- `x = 99` (syntax: `<name> = <integer value>`) -- assign a variable to some integer value.
- `x = y` (syntax: `<name> = <another name>`) -- assign a variable to some other variable's value.
- `scope {` -- open a scope
- `}`  -- exit the last opened scope.
- `print x` (syntax: `print <variable name>`) -- prints the variable's name on the screen or prints "null" if the variable doesn't exist.


### Example Code

```code
x = 1
print x
scope {
 x = 2
 print x
 scope {
   x = 3
   y = x
   print x
   print y
 }
 print x
 print y
}
print x
```

### Output

```
1
2
3
3
2
null
1
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
- **Gradle**: The project uses Gradle for building. You can install it from [here](https://gradle.org/install/).

### Checkout the Project

To clone the repository, run the following command:

```sh
git clone https://github.com/yourusername/interpreter-project.git
cd interpreter-project
```

### Build the Project

To build the project, use the Gradle wrapper included in the repository:

```sh
./gradlew build
```

On Windows, use:

```sh
gradlew.bat build
```

### Run the Interpreter

To run the interpreter with a source file, use the following command:

```sh
./gradlew run --args="path/to/sourcefile"
```

On Windows, use:

```sh
gradlew.bat run --args="path/to/sourcefile"
```

Replace `path/to/sourcefile` with the path to your source code file written in the custom language.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.
