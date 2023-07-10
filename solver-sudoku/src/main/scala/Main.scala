import zio.json._
import zio._
import scala.compiletime.ops.int
import java.io.IOException
import scala.compiletime.ops.any
import scala.io.Source
import java.io.PrintWriter
import java.io.File

object Main extends ZIOAppDefault {
  // Define type aliases for clarity
  // Represents the Sudoku grid as a 2D list of optional integers.
  // Each element can either be Some(value) if the cell is filled with a digit,
  // or None if the cell is empty.
  type Grid = List[List[Option[Int]]]
  type Position = (Int, Int)
  type SudokuData  = (IO[String, Sudoku], Grid)

  // Define the Sudoku case class for JSON serialization/deserialization
  case class Sudoku(RawData: List[List[Int]])

  object Sudoku {
    // Define JSON codecs for the Sudoku case class
    implicit val decoder: JsonDecoder[Sudoku] =
      DeriveJsonDecoder.gen[Sudoku]

    implicit val encoder: JsonEncoder[Sudoku] =
      DeriveJsonEncoder.gen[Sudoku]
  }

  // Read the JSON file and return the parsed Sudoku data along with the raw grid
  def readSudokuFromJson(path: String): SudokuData  = {
    val file = new File(path)
    if (!file.exists() || !file.isFile) { // handle invalid file
      println("Invalid file path")
      return null
    }

    val fileContent = Source.fromFile(file).mkString
    println(s"$fileContent \n")

    val sudoku = fileContent.fromJson[Sudoku]

    val jsonIO = ZIO.fromEither(fileContent.fromJson[Sudoku])

    sudoku match {
      case Right(i) =>
        val rawdata = i.RawData.map(row => row.map(value => if (value == 0) None else Some(value)))

        // Perform a simple validation to check if the grid has a valid size
        if (rawdata.length == 9 && rawdata.forall(_.length == 9)) {
          println("Successfully parsed and validated JSON input")
          (jsonIO, rawdata)
        } else {
          println("Invalid Sudoku grid")
          null
        }
      case Left(i) =>
        println(s"Error: $i")
        null
    }
  }


  // Check if a digit is valid in a specific position of the grid
  def isDigitValid(grid: Grid, digit: Int, position: Position): Boolean = {
    val (row, col) = position

    val rowValid = !grid(row).exists {
      case Some(value) => value == digit
      case None => false
    }
    val colValid = !grid.exists {
      case rowValues => rowValues(col).contains(digit)
    }
    val subgridValid = {
      val startRow = (row / 3) * 3
      val startCol = (col / 3) * 3
      val subgrid = grid
        .slice(startRow, startRow + 3)
        .flatMap(rowValues => rowValues.slice(startCol, startCol + 3))
      !subgrid.exists {
        case Some(value) => value == digit
        case None => false
      }
    }

    rowValid && colValid && subgridValid
  }


 
  // Pretty print the grid
  def prettyPrint(grid: Grid): Unit = {
    println("+-------+-------+-------+")
    for (i <- grid.indices) {
      if (i % 3 == 0 && i != 0)
        println("+-------+-------+-------+")
      for (j <- grid(i).indices) {
        if (j % 3 == 0 && j != 0)
          print("| ")
        grid(i)(j) match {
          case Some(value) => print(s"$value ")
          case None => print("  ")
        }
      }
      println()
    }
    println("+-------+-------+-------+")
    println()
  }

  // Solve the Sudoku puzzle and handle the result
  def solveSudoku(grid: Grid): ZIO[Any, Any, Any] = {
    ZIO.succeed(println("Resolving problem...")) *>
      ZIO.fromOption(solveSudokuGrid(grid)).fold(
        _ => Console.print("No solution found!"),
        solution => {
          prettyPrint(grid)
          prettyPrint(solution)
          saveSudokuToJson(solution)
        }
      )
  }

  // Application entry point
  def run: ZIO[Any, Any, Unit] =
    for {
      _ <- Console.print("Please enter a file:") // Prompt the user for the JSON file path
      path <- Console.readLine // Read the user's input
      _ <- Console.printLine(s"You entered: $path") // Display the entered path
      gridIO = readSudokuFromJson(path) // Read the Sudoku data from the JSON file
      _ <- gridIO match {
        case (_, null) => ZIO.unit // Skip solving if the grid is null
        case (_, grid) => solveSudoku(grid) // Solve the Sudoku puzzle using the extracted grid data
      }
    } yield ()
}
