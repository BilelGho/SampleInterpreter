sealed class Command {
    data class Assign(val variable: String, val expression: Expression) : Command()
    data class Print(val variable: String) : Command()
    object OpenScope : Command()
    object CloseScope : Command()

    fun parseCommand(input: String): Command {
        val tokens = input.split(" ").filter(String::isNotBlank)
        return when {
            tokens.size == 3 && tokens[1] == "=" -> {
                val variable = tokens[0]
                val expression = if (tokens[2].toIntOrNull() != null) {
                    Expression.Number(tokens[2].toInt())
                } else {
                    Expression.Variable(tokens[2])
                }
                Assign(variable, expression)
            }
            tokens.size == 2 && tokens[0] == "print" -> Print(tokens[1])
            tokens.size == 2 && tokens[0] == "scope" && tokens[1] == "{" -> OpenScope
            tokens.size == 1 && tokens[0] == "}" -> CloseScope
            else -> throw IllegalArgumentException("Invalid command")
        }
    }

    fun parseProgram(input: String): List<Command> {
        val commands = mutableListOf<Command>()
        val lines = input.split("\n")
        for (line in lines) {
            if (line.isNotBlank()) {
                commands.add(parseCommand(line))
            }
        }
        return commands
    }
}

sealed class Expression {
    data class Number(val value: Int) : Expression()

    data class Variable(val name: String) : Expression()
}