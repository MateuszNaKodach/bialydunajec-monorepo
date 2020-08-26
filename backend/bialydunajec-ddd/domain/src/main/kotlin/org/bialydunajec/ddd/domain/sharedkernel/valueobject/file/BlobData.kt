package org.bialydunajec.ddd.domain.sharedkernel.valueobject.file

import org.apache.commons.codec.digest.DigestUtils
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainError
import org.springframework.util.Base64Utils
import javax.persistence.Embeddable
import javax.persistence.Lob

@Embeddable
class BlobData private constructor(
        @field:Lob
        private val bytes: ByteArray,
        private val md5CheckSum: String
) {

    override fun toString(): String {
        return Base64Utils.encodeToString(bytes)
    }

    companion object {

        fun fromBase64(base64: String, md5CheckSum: String): BlobData {
            validateMd5CheckSumForBase64(base64, md5CheckSum)
            val decodeBytes = Base64Utils.decodeFromString(base64)
            return BlobData(decodeBytes, md5CheckSum)
        }

        private fun validateMd5CheckSumForBase64(base64: String, md5CheckSum: String?) {
            if (md5CheckSum == null || md5CheckSum != DigestUtils.md5Hex(base64)) {
                throw DomainRuleViolationException.of(SharedKernelDomainError.BASE_64_IS_CORRUPTED)
            }
        }
    }
}
