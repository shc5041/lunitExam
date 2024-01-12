package com.example.lunitexam.service;

import com.example.lunitexam.model.dao.SlideInfo;
import com.example.lunitexam.repository.SlideInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlideInfoService {

    @Value("${spring.servlet.multipart.location}") // application 의 properties 의 변수
    private String uploadPath;
    private final SlideInfoRepository slideInfoRepository;

    public Page<SlideInfo> findByUserId(String userId, Pageable pageable) {
        return slideInfoRepository.findByUserId(userId, pageable);
    }

    public Page<SlideInfo> findByUserIdAndOriginFileName(String userId, String originFileName, Pageable pageable) {
        return slideInfoRepository.findByUserIdAndOriginFileName(userId, originFileName, pageable);
    }

    public Page<SlideInfo> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return slideInfoRepository.findByUserIdAndCreatedDateBetween(userId, startDateTime, endDateTime, pageable);
    }

    @SneakyThrows
    public Boolean uploadFile(String userId, MultipartFile multipartFile) {

        String originalName = Objects.requireNonNull(multipartFile.getOriginalFilename()).trim();

        log.info("fileName: " + originalName);

        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // 저장할 파일 이름 중간에 "_"를 이용해서 구현
        String path = uploadPath + File.separator + folderPath + File.separator + originalName;

        Path savePath = Paths.get(path);
        Files.deleteIfExists(savePath);
        boolean isUpload = copyFile(multipartFile, savePath);
        SlideInfo slideInfo = SlideInfo.builder()
                .bytes(multipartFile.getSize())
                .originFileName(originalName)
                .path(path)
                .isUpload(isUpload)
                .userId(userId)
                .build();
        Optional<SlideInfo> slideInfoOptional = slideInfoRepository.findByPath(path);
        slideInfoOptional.ifPresent(info -> slideInfo.setIdx(info.getIdx()));
        slideInfoRepository.save(slideInfo);

        return isUpload;
    }

    public Resource downloadFile(Long idx, String fileName) throws IOException {
        Optional<SlideInfo> slideInfoOptional = slideInfoRepository.findByIdxAndOriginFileName(idx, fileName);
        Resource resource = null;
        if (slideInfoOptional.isPresent()) {
            SlideInfo slideInfo = slideInfoOptional.get();
            resource = getFileAsResource(slideInfo.getPath());

            // 파일 다운로드를 위한 ResponseEntity 생성

        }
        return resource;
    }

    private boolean copyFile(MultipartFile multipartFile, Path savePath) {
        boolean isUpload = false;
        int bufferSize = 1024 * 8;
        try (BufferedInputStream bis = new BufferedInputStream(multipartFile.getInputStream());
             BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(savePath))) {

            byte[] buffer = new byte[bufferSize]; // 버퍼 크기 설정
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            isUpload = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    /*날짜 폴더 생성*/
    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
            log.info("-------------------makeFolder------------------");
            log.info("uploadPathFolder.exists(): " + uploadPathFolder.exists());
            log.info("mkdirs: " + mkdirs);
        }

        return folderPath;
    }

    public Resource getFileAsResource(String path) throws IOException {
//        AtomicReference<Path> foundFile = null;
        Path dirPath = Paths.get(path);
        if(dirPath.toFile().exists()){
            return new UrlResource(dirPath.toUri());
        }
        return null;
    }
}
