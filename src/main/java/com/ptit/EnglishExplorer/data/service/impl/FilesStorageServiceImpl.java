package com.ptit.EnglishExplorer.data.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.ptit.EnglishExplorer.data.service.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public String save(MultipartFile file) {
        try {
            // Lấy tên file gốc và chuẩn hóa
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("Tên file không hợp lệ");
            }

            // Xử lý tên file: thay thế khoảng trắng bằng dấu gạch dưới và loại bỏ ký tự đặc biệt
            String sanitizedFileName = originalFileName.replaceAll("\\s+", "_") // Thay thế khoảng trắng bằng dấu gạch dưới
                    .replaceAll("[^a-zA-Z0-9_\\.\\-]", ""); // Loại bỏ ký tự không hợp lệ

            // Tạo đường dẫn cho file
            Path filePath = this.root.resolve(sanitizedFileName);

            // Nếu file đã tồn tại, tạo tên file mới
            if (Files.exists(filePath)) {
                String fileNameWithoutExt = sanitizedFileName.substring(0, sanitizedFileName.lastIndexOf('.'));
                String fileExtension = sanitizedFileName.substring(sanitizedFileName.lastIndexOf('.'));
                String newFileName = fileNameWithoutExt + "_" + System.currentTimeMillis() + fileExtension;
                filePath = this.root.resolve(newFileName);
            }

            // Lưu file
            Files.copy(file.getInputStream(), filePath);

            // Trả về tên file mới đã lưu
            return filePath.getFileName().toString();
        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu file: " + e.getMessage());
        }
    }


    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}