package com.succraft.luferau.ecdss

data class BodyToSign(val fileType: String, val content: String) {
    val isValid: Boolean
        get() = getSignType() != SignType.UNDEFINED &&
                this.content.isNotEmpty()

    fun getSignType() =
            when(this.fileType) {
                "text/xml", "application/xml" -> SignType.XML
                "application/pdf" -> SignType.PDF
                "application/msword" -> SignType.DOC
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> SignType.DOCX
                else -> SignType.UNDEFINED
            }
}

enum class SignType {
    XML, PDF, DOC, DOCX, UNDEFINED;
}