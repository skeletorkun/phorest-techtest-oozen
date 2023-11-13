package com.phorest.appointment.service

import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader

interface CsvService {
    fun throwIfFileEmpty(file: MultipartFile)
    fun closeFileReader(fileReader: BufferedReader?)

    fun <T> uploadCsvFile(file: MultipartFile, type: Class<T>): MutableList<T>
}
