package com.example.currencyexchangeapp.domain.use_case.base

interface Mapper<in P, out R> {

    suspend fun map(param: P): R
}

suspend fun <R> Mapper<Nothing?, R>.map() = map(null)