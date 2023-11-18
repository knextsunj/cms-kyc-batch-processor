package com.github.knextsunj.cmskycbatchprocessor.exception

open class ProcessingException : RuntimeException {

    constructor(message: String?) : super(message)

    constructor(message: String?, throwable: Throwable?) : super(message, throwable)
}