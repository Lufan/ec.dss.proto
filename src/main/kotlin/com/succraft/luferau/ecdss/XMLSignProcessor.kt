package com.succraft.luferau.ecdss

import eu.europa.esig.dss.*
import eu.europa.esig.dss.xades.XAdESSignatureParameters
import eu.europa.esig.dss.xades.signature.XAdESService
import eu.europa.esig.dss.validation.CommonCertificateVerifier
import org.apache.commons.io.IOUtils


class XMLSignProcessor: ISigningProcessor {
    override fun sign(input: ByteArray): ByteArray {
        val toSignDocument = InMemoryDocument(input)

        val parameters = XAdESSignatureParameters()
        parameters.signatureLevel = SignatureLevel.XAdES_BASELINE_B
        parameters.signaturePackaging = SignaturePackaging.ENVELOPED
        parameters.digestAlgorithm = DigestAlgorithm.SHA256
        parameters.signingCertificate = privateKey.certificate
        parameters.certificateChain = privateKey.certificateChain.toMutableList()

        val service = XAdESService(CommonCertificateVerifier())
        val signatureValue = token.sign(
                service.getDataToSign(toSignDocument, parameters),
                parameters.digestAlgorithm,
                privateKey
        )
        val signedDocument = service.signDocument(toSignDocument, parameters, signatureValue)
        return IOUtils.toByteArray(signedDocument.openStream())
    }
}