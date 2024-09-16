package com.example.currencyexchangeapp.domain.use_case.base

interface UseCaseBlocking<in P, out R> {

    fun execute(param: P): R
}

fun <R> UseCaseBlocking<Nothing?, R>.execute() = execute(null)