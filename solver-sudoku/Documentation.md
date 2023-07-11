# Sudoku Solver

The Sudoku Solver is a command-line application that solves Sudoku puzzles provided in JSON format. It uses a backtracking algorithm to find the solution and saves the solved puzzle as a JSON file.

## Purpose

The purpose of this application is to solve Sudoku puzzles programmatically. It provides a convenient way to input Sudoku problems from a JSON file and obtain the solution.

## Functionality

The Sudoku Solver has the following functionality:

- Reads a Sudoku problem from a JSON file.
- Validates the JSON file path and checks if it exists.
- Parses the JSON file and extracts the Sudoku problem data.
- Solves the Sudoku problem using a backtracking algorithm.
- Prints the initial and solved grids to the console.
- Saves the solved grid as a JSON file.
- Invalid Sudoku  Grid

## Usage

To use the Sudoku Solver, follow these steps:

1. Prepare a JSON file containing the Sudoku problem. The file should have the following structure:

```json
{
  "RawData": [
    [1, 0, 0, 0, 7, 0, 0, 3, 0],
    [0, 0, 5, 0, 0, 0, 0, 8, 0],
    [0, 0, 0, 0, 1, 0, 6, 0, 0],
    [0, 0, 0, 0, 0, 5, 3, 0, 0],
    [0, 0, 9, 2, 0, 1, 5, 0, 0],
    [0, 0, 8, 9, 0, 0, 0, 0, 0],
    [0, 7, 0, 0, 6, 0, 0, 0, 0],
    [0, 2, 0, 0, 0, 0, 9, 0, 0],
    [0, 4, 0, 0, 8, 0, 0, 0, 0]
  ]
}
```

## External Libraries

The Sudoku Solver uses the following external libraries:

    ZIO: ZIO is a functional effect system for Scala. It provides a powerful concurrency model and error handling capabilities.
    zio-json: zio-json is a JSON library for Scala built on top of ZIO. It provides convenient JSON encoding and decoding functionalities.

These libraries are used for reading and parsing JSON files and handling the ZIO-based application flow.

## Additional Setup

Before running the Sudoku Solver, make sure you have the following installed:

    Scala programming language
    Scala Build Tool (SBT) or a compatible build tool

Ensure that the necessary dependencies (ZIO and zio-json) are specified in your build configuration file.


## Limitations

    The Sudoku Solver assumes that the input Sudoku problem is valid and has a unique solution. It does not check for multiple solutions or invalid puzzles.
    The application currently supports 9x9 Sudoku puzzles only. Other grid sizes are not supported.

# RUN
1.   Compile and run the application code using a Scala compiler or an integrated development environment (IDE).

2.   When prompted, enter the path to the JSON file containing the Sudoku problem (Absolute Path).

3.   The application will print the initial grid to the console.

4.   The application will attempt to solve the Sudoku problem. If a solution is found, the solved grid will be printed to the console, and a file named "solved.json" will be created with the solved grid in JSON format.

## Output :
### Solvable soduku
![Screenshot from 2023-07-09 21-01-22.png](Images%2FScreenshot%20from%202023-07-09%2021-01-22.png)
![Screenshot from 2023-07-09 21-02-13.png](Images%2FScreenshot%20from%202023-07-09%2021-02-13.png)
### invalid grid 
![Screenshot from 2023-07-09 21-01-22.png](Images%2FScreenshot%20from%202023-07-09%2021-01-22.png)
### invalid Path!
![Screenshot from 2023-07-09 21-15-54.png](Images%2FScreenshot%20from%202023-07-09%2021-15-54.png)