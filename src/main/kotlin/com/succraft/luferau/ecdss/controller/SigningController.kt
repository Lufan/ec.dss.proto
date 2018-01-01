package com.succraft.luferau.ecdss.controller

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.succraft.luferau.ecdss.model.BodyToSign
import com.succraft.luferau.ecdss.model.SignedBody
import com.succraft.luferau.ecdss.service.SignatureService
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class SigningController {
    val counter = AtomicLong()
    private val logger = LogFactory.getLog(SigningController::class.java)


    @Autowired
    lateinit var signatureService: SignatureService

    @PostMapping("/sign")
    fun signing(@RequestBody body: String): SignedBody {
        counter.incrementAndGet()

        val parsedBody: BodyToSign?
        try {
            parsedBody = Gson().fromJson(body, BodyToSign::class.java)
        } catch (ex: JsonSyntaxException) {
            logger.error("POST SigningController: ${ex.message ?: "Unknown error."}")
            return SignedBody(
                    "",
                    ex.message ?: "Unknown error.",
                    counter.toString()
            )
        }
        return if (parsedBody != null && parsedBody.isValid) {
            logger.info("POST SigningController: sign document.")
            SignedBody(
                    signatureService.signDocument(parsedBody),
                    null,
                    counter.toString()
            )
        } else {
            logger.info("POST SigningController: invalid document.")
            SignedBody(
                    "",
                    "Invalid request.",
                    counter.toString()
            )
        }
    }
}


