package com.example.flowapi.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileService {

    private val rootLocation: Path = Paths.get("uploads")
    private val serverUrl = "http://79.174.80.34"  // Захардкоженный IP-адрес и схема

    init {
        Files.createDirectories(rootLocation)
    }

    fun saveFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw RuntimeException("Failed to store empty file")
        }
        val destinationFile = rootLocation.resolve(Paths.get(file.originalFilename))
            .normalize().toAbsolutePath()
        file.inputStream.use { inputStream ->
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
        }
        // Возвращаем полный URL для сохранения в базу данных
        return "$serverUrl/images/" + file.originalFilename
    }
}
