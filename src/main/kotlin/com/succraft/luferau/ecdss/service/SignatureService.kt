package com.succraft.luferau.ecdss.service

import com.succraft.luferau.ecdss.model.BodyToSign
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class SignatureService {

    @Autowired
    lateinit var signingFactory: SigningFactory

    fun signDocument(document: BodyToSign) = if (document.isValid) {
        val signProcessor = signingFactory.getSigningProcessor(document.getSignType())
        val fileData = Base64.decodeBase64(
                document.content.toByteArray(Charset.forName("UTF-8"))
        )
        Base64.encodeBase64String(signProcessor.sign(fileData))
    } else {
        ""
    }
}