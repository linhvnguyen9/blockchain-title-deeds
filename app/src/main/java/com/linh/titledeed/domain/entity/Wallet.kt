package com.linh.titledeed.domain.entity

import java.math.BigInteger

data class Wallet(val password: String, val mnemonic: String, val privateKey: String, val address: String, val balance: BigInteger = BigInteger.ZERO)