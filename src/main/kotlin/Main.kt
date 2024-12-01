package org.example

import Command.CloseScope.parseCommand
import Command.CloseScope.parseProgram
import Interpreter
import java.io.File

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    if (args.isNotEmpty()) {
        val fileName = args[0]
        val program = parseProgram(File(fileName).bufferedReader().use { it.readText() })
        val result = interpreter.runProgram(program)
        println(result)
    } else {
        runREPL(interpreter)
    }
}

fun runREPL(interpreter: Interpreter) {
    println("Enter your program (end with an empty line), type  \":exit\" to exit REPL:")
    val reader = System.`in`.bufferedReader()
    var exit = false

    val isExit: (String) -> Boolean = { line: String ->
        val b = line.trim() == ":exit"
        exit = exit || b
        b
    }
    while (!exit) {
        print(">>> ")
        reader.lines().takeWhile{it.isNotBlank() && !isExit(it) }.forEach(
            fun(line) {
                if (line.isBlank()) {
                    return
                }

                if (isExit(line)) {
                    exit = true
                    return
                }
                val command = parseCommand(line)
                try {
                    val result = interpreter.runCommand(command)
                    result.ifPresent(::println)
                } catch (e: IllegalArgumentException) {
                    println("Invalid command: ${e.message}")
                } catch (e: Exception) {
                    println("An error occurred: ${e.message}")
                }
            }
        )
    }
}