package com.succraft.luferau.ecdss.service

import com.succraft.luferau.ecdss.model.SignType
import org.springframework.stereotype.Service

@Service
class SigningFactory {
    fun getSigningProcessor(type: SignType): ISigningProcessor =
            when (type) {
                SignType.XML -> XMLSignProcessor()
                SignType.PDF, SignType.DOC, SignType.DOCX, SignType.UNDEFINED -> XMLSignProcessor()
            }
}
