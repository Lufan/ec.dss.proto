package com.succraft.luferau.ecdss

import eu.europa.esig.dss.token.DSSPrivateKeyEntry
import eu.europa.esig.dss.token.Pkcs12SignatureToken
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

val token = Pkcs12SignatureToken("keys/ia.p12", "")
val privateKey: DSSPrivateKeyEntry
    get() = token.keys[0]


fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}