package dev.silas.sqssample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqsSampleApplication

fun main(args: Array<String>) {
	runApplication<SqsSampleApplication>(*args)
}
