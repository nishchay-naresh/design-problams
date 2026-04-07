package com.nishchay.file.controller;

import com.nishchay.file.pojo.XFile;
import com.nishchay.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @PostMapping("/uploader")
    public ResponseEntity<?> upload(
            @RequestParam String fileGroup,
            @RequestParam List<MultipartFile> files) throws IOException {

        service.upload(fileGroup, files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/downloader")
    public ResponseEntity<?> download(@RequestParam String fileGroup) throws IOException {

        List<XFile> files = service.download(fileGroup);

        if (files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Single file
        if (files.size() == 1) {
            XFile file = files.get(0);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + file.getFileName())
                    .body(file.getFile());
        }

        // Multiple files → ZIP
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (XFile file : files) {
            ZipEntry entry = new ZipEntry(file.getFileName());
            zos.putNextEntry(entry);
            zos.write(file.getFile());
            zos.closeEntry();
        }

        zos.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + fileGroup + ".zip")
                .body(baos.toByteArray());
    }
}