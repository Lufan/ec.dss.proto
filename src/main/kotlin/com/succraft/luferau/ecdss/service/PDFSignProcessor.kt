package com.succraft.luferau.ecdss.service

import com.succraft.luferau.ecdss.privateKey
import com.succraft.luferau.ecdss.token
import eu.europa.esig.dss.DigestAlgorithm
import eu.europa.esig.dss.InMemoryDocument
import eu.europa.esig.dss.SignatureLevel
import eu.europa.esig.dss.SignaturePackaging
import eu.europa.esig.dss.pades.PAdESSignatureParameters
import eu.europa.esig.dss.pades.SignatureImageParameters
import eu.europa.esig.dss.pades.SignatureImageTextParameters
import eu.europa.esig.dss.pades.signature.PAdESService
import eu.europa.esig.dss.validation.CommonCertificateVerifier
import org.apache.commons.io.IOUtils
import java.awt.Color
import java.awt.Font


class PDFSignProcessor: ISigningProcessor {
    override fun sign(input: ByteArray): ByteArray {
        val toSignDocument = InMemoryDocument(input)

        val parameters = PAdESSignatureParameters()
        parameters.signatureLevel = SignatureLevel.PAdES_BASELINE_B
        parameters.signaturePackaging = SignaturePackaging.ENVELOPED
        parameters.digestAlgorithm = DigestAlgorithm.SHA256
        parameters.signingCertificate = privateKey.certificate
        parameters.certificateChain = privateKey.certificateChain.toMutableList()

        val imageParameters = SignatureImageParameters()
        imageParameters.setxAxis(20f)
        imageParameters.setyAxis(5f)
        val textParameters = SignatureImageTextParameters()
        textParameters.font = Font("serif", Font.PLAIN, 14)
        textParameters.textColor = Color.BLUE
        textParameters.text = "My visual signature"
        imageParameters.textParameters = textParameters

        parameters.signatureImageParameters = imageParameters

        val service = PAdESService(CommonCertificateVerifier())
        val signatureValue = token.sign(
                service.getDataToSign(toSignDocument, parameters),
                parameters.digestAlgorithm,
                privateKey
        )
        val signedDocument = service.signDocument(toSignDocument, parameters, signatureValue)
        return IOUtils.toByteArray(signedDocument.openStream())
    }
}