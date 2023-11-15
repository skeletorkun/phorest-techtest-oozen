package com.phorest.appointment.service.impl

import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import com.phorest.appointment.exception.BadRequestException
import com.phorest.appointment.exception.CsvImportException
import com.phorest.appointment.service.CsvService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@Service
class CsvServiceImpl : CsvService {

    override fun throwIfFileEmpty(file: MultipartFile) {
        if (file.isEmpty)
            throw BadRequestException("Empty file")
    }

    override fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw CsvImportException("Error during csv import")
        }
    }

    override fun <T> uploadCsvFile(file: MultipartFile, type: Class<T>): MutableList<T> {
        throwIfFileEmpty(file)
        return uploadCsvFile(file.inputStream, type)
    }

    fun <T> uploadCsvFile(inputStream: InputStream, type: Class<T>): MutableList<T> {
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(inputStream))
            val csvToBean = createCSVToBean(fileReader, type)
            return csvToBean.parse()
        } catch (ex: Exception) {
            throw CsvImportException("Error during csv import")
        } finally {
            closeFileReader(fileReader)
        }
    }

    fun <T> createCSVToBean(fileReader: BufferedReader?, type: Class<T>): CsvToBean<T> =
        CsvToBeanBuilder<T>(fileReader)
            .withType(type)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
}