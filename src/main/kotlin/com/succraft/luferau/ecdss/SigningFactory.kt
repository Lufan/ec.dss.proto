package com.succraft.luferau.ecdss

fun getSigningProcessor(type: SignType): ISigningProcessor =
        when(type) {
            SignType.XML -> XMLSignProcessor()
            SignType.PDF, SignType.DOC, SignType.DOCX, SignType.UNDEFINED -> XMLSignProcessor()
        }

