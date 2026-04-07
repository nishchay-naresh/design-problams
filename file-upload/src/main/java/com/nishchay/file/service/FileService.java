package com.nishchay.file.service;

import com.nishchay.file.pojo.XFile;
import com.nishchay.file.repo.XFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final XFileRepository repo;

    public void upload(String fileGroup, List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            Optional<XFile> existing =
                    repo.findByFileGroupAndFileName(fileGroup, file.getOriginalFilename());

            XFile xFile = existing.orElse(new XFile());

            xFile.setFileGroup(fileGroup);
            xFile.setFileName(file.getOriginalFilename());
            xFile.setFile(file.getBytes());

            repo.save(xFile);
        }
    }

    public List<XFile> download(String fileGroup) {
        return repo.findByFileGroup(fileGroup);
    }
}