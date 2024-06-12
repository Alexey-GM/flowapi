package com.example.flowapi.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileService {

    private val rootLocation: Path = Paths.get("uploads")

    init {
        Files.createDirectories(rootLocation)
    }

    fun saveFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw RuntimeException("Failed to store empty file")
        }
        val destinationFileName = file.originalFilename!!
        val destinationFile = rootLocation.resolve(destinationFileName)
            .normalize().toAbsolutePath()
        file.inputStream.use { inputStream ->
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
        }
        return "http://79.174.80.34/images/$destinationFileName"
    }
}
