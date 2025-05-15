package com.example.skillshareeeeeeee.serviceimpl;

import com.example.skillshareeeeeeee.services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    // Répertoire où les fichiers seront sauvegardés
    @Value("${C:\\Users\\takwa\\IdeaProjects\\skillshareeeeeeee}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        // Créer le dossier s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Nettoyer le nom du fichier
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = System.currentTimeMillis() + "_" + originalFilename;

        // Chemin complet du fichier à sauvegarder
        Path filePath = uploadPath.resolve(fileName);

        // Copier le fichier vers le répertoire de destination
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner un chemin relatif ou une URL
        return "/uploads/" + fileName; // Cette URL sera accessible via un endpoint
    }
}