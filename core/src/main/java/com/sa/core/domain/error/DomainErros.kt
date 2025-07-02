package com.sa.core.domain.error

sealed class DomainError : Throwable() {

    data object NoConnection : DomainError()

    data object Timeout : DomainError()

    data object ServerError : DomainError()

    data object EmptyResult : DomainError()

    data class Unknown(override val message: String? = null) : DomainError()
}