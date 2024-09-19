package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class LibraryAppApplication

fun main(args: Array<String>) {
    runApplication<LibraryAppApplication>(*args)
}

/**
 * kotlin main 확장 함수 사용
 * runApplication<LibraryAppApplication>(*args)
 */