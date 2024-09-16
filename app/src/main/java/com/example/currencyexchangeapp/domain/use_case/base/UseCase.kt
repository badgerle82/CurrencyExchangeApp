package com.example.currencyexchangeapp.domain.use_case.base

interface UseCase<in P, out R> {

    suspend fun execute(param: P): R
}

suspend fun <R> UseCase<Nothing?, R>.execute() = execute(null)