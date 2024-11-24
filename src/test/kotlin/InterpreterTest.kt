import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InterpreterTest {

    @Test
    fun assignCommandUpdatesVariableMap() {
        val interpreter = Interpreter()
        val command = Command.Assign("x", Expression.Number(5))
        interpreter.runCommand(command)
        assertEquals(5, interpreter.current_variable_map["x"])
    }

    @Test
    fun printCommandPrintsVariableValue() {
        val interpreter = Interpreter()
        interpreter.current_variable_map["x"] = 10
        val command = Command.Print("x")
        val result = interpreter.runCommand(command)
        assertEquals(Optional.of("10"), result)
    }

    @Test
    fun openScopeCommandPushesCurrentVariableMap() {
        val interpreter = Interpreter()
        interpreter.current_variable_map["x"] = 10
        val command = Command.OpenScope
        interpreter.runCommand(command)
        assertTrue(interpreter.variable_maps.isNotEmpty())
        assertEquals(10, interpreter.variable_maps.peek()["x"])
    }

    @Test
    fun closeScopeCommandPopsVariableMap() {
        val interpreter = Interpreter()
        interpreter.current_variable_map["x"] = 10
        interpreter.runCommand(Command.OpenScope)
        interpreter.current_variable_map["x"] = 20
        interpreter.runCommand(Command.CloseScope)
        assertEquals(10, interpreter.current_variable_map["x"])
    }

    @Test
    fun runProgramExecutesAllCommands() {
        val interpreter = Interpreter()
        val program = listOf(
            Command.Assign("x", Expression.Number(5)),
            Command.Print("x"),
            Command.OpenScope,
            Command.Assign("x", Expression.Number(10)),
            Command.Print("x"),
            Command.CloseScope,
            Command.Print("x")
        )
        val result = interpreter.runProgram(program)
        assertEquals(listOf("5","10","5"), result)
    }

    @Test
    fun runProgramParsesAndExecutesStringProgram() {
        val interpreter = Interpreter()
        val program = "x = 5 \n" +
                      "print x\n" +
                      "scope { \n"+
                      "x = 10\n" +
                      "print x \n" +
                      "} \n" +
                      "print x"
        val result = interpreter.runProgram(program)
        assertEquals(listOf("5","10","5"), result)
    }
}