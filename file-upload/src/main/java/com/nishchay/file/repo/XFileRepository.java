package com.nishchay.file.repo;

import com.nishchay.file.pojo.XFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface XFileRepository extends JpaRepository<XFile, Long> {

    Optional<XFile> findByFileGroupAndFileName(String fileGroup, String fileName);

    List<XFile> findByFileGroup(String fileGroup);
}