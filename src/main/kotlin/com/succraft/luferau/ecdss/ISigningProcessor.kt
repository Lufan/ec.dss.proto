package com.succraft.luferau.ecdss

interface ISigningProcessor {
    fun sign(input: ByteArray): ByteArray
}