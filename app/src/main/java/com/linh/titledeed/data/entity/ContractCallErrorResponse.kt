package com.linh.titledeed.data.entity

import java.lang.Exception

data class ContractCallErrorResponse(
    val error: ContractCallError?
)

data class ContractCallError(
    val message: String,
    val code: Int,
)

class TokenOwnerException(message: String, val code: Int): Exception(message)
class InsufficientGasException(message: String, val code: Int): Exception(message)
class InsufficientFundSentException(message: String, val code: Int): Exception(message)