package org.bialydunajec.ddd.application.base.file

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.file.BlobData

interface FileStorage {

    fun store(fileName: String, fileData: BlobData)

}