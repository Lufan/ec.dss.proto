package com.succraft.luferau.ecdss.service

interface ISigningProcessor {
    fun sign(input: ByteArray): ByteArray
}