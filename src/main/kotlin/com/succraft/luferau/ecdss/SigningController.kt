package com.succraft.luferau.ecdss

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.apache.commons.codec.binary.Base64
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicLong

@RestController
class SigningController {
    val counter = AtomicLong()

    @PostMapping("/sign")
    fun signing(@RequestBody body: String): SignedBody? {
        var parsedBody: BodyToSign?
        try {
            parsedBody = Gson().fromJson(body, BodyToSign::class.java)
        } catch (ex: JsonSyntaxException) {
            return SignedBody(
                    "",
                    if (ex.message != null) ex.message!! else "Unknown error." ,
                    counter.toString()
            )
        }
        println(parsedBody?.fileType ?: "null")
        if (parsedBody != null && parsedBody.isValid) {
            val signProcessor = getSigningProcessor(parsedBody.getSignType())
            val fileData = Base64.decodeBase64(
                    parsedBody.content.toByteArray(Charset.forName("UTF-8"))
            )

            println("decoded data start")
            println(String(fileData, Charset.forName("UTF-8")))
            println("decoded data end")

            val signedDocByteArray = signProcessor.sign(fileData)
            return SignedBody(
                    Base64.encodeBase64String(signedDocByteArray),
                    null,
                    counter.toString()
            )
        }
        return SignedBody(
                "",
                "Invalid request.",
                counter.toString()
        )
    }
}


