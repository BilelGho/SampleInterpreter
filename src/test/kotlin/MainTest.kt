import Command.CloseScope.parseProgram
import org.example.runREPL
import org.junit.jupiter.api.Assertions.assertLinesMatch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals


class MainTest {
    val TEST_PROGRAM =
        """
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
        """.trimIndent()
    @Test
    fun test() {
        val interpreter = Interpreter()
        val result = interpreter.runProgram(TEST_PROGRAM)
        assertEquals(listOf("1", "2", "3", "3", "2", "null", "1"), result)
    }

    @Test
    fun runProgramFromFile() {
        val interpreter = Interpreter()
        val fileName = "test_program.txt"
        File(fileName).writeText(TEST_PROGRAM)
        val program = parseProgram(File(fileName).bufferedReader().use { it.readText() })
        val result = interpreter.runProgram(program)
        assertEquals(listOf("1", "2", "3", "3", "2", "null", "1"), result)
        File(fileName).delete()
    }

    @Test
    fun runREPLWithMultipleCommands() {
        val interpreter = Interpreter()
        val input = """
            x = 1
            print x
            scope {
                x = 2
                print x
            }
            print x
            :exit
        """.trimIndent()
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        runREPL(interpreter)
        val expectedOutput = """
            Enter your program (end with an empty line):
            >>> 1
            2
            1
            """.trimIndent()
        assertLinesMatch(expectedOutput.lines(), outputStream.toString().trim().lines())
    }
}