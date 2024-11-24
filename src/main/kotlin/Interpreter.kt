import Command.CloseScope.parseProgram
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import java.util.*
class Interpreter {
    var current_variable_map = persistentMapOf<String,Int>()
    val variable_maps = Stack<PersistentMap<String, Int>>()

    fun runCommand(command: Command) : Optional<String> {
        when (command) {
            is Command.Assign -> {
                val value = command.expression.eval(current_variable_map)
                if (value != null) {
                    current_variable_map = current_variable_map.put(command.variable, value)
                }
            }
            is Command.Print -> {
                val value = current_variable_map[command.variable]
                return Optional.of(value.toString())
            }
            is Command.OpenScope -> {
                variable_maps.push(current_variable_map)
            }
            is Command.CloseScope -> {
                current_variable_map = variable_maps.pop()
            }
        }
        return Optional.empty()
    }

    fun runProgram(program: List<Command>) : List<String> {
        return program.map(this::runCommand).filter(Optional<String>::isPresent).map(Optional<String>::get)
    }

    fun runProgram(program: String) : List<String> {
        return runProgram(parseProgram(program))
    }
}