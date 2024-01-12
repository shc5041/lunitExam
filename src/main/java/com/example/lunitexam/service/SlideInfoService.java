package com.example.lunitexam.service;

import com.example.lunitexam.exception.ErrorCode;
import com.example.lunitexam.exception.LunitExamException;
import com.example.lunitexam.model.dao.AnalysisDecision;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class SlideInfoService {

    @Value("${spring.servlet.multipart.location}") // application 의 properties 의 변수
    private String uploadPath;
    private final SlideInfoRepository slideInfoRepository;

    public Page<SlideInfo> findAll(Pageable pageable) {
        return slideInfoRepository.findAll(pageable);
    }

    public Page<SlideInfo> findByUserId(String userId, Pageable pageable) {
        return slideInfoRepository.findByUserId(userId, pageable);
    }

    public Page<SlideInfo> findByUserIdAndOriginFileNameContaining(String userId, String originFileName, Pageable pageable) {
        return slideInfoRepository.findByUserIdAndOriginFileNameContaining(userId, originFileName, pageable);
    }
    public Page<SlideInfo> findByUserIdAndOriginFileNameContainingAndCreatedDateBetween(String originFileName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return slideInfoRepository.findByUserIdAndOriginFileNameContainingAndCreatedDateBetween(originFileName,startDate, endDate, pageable);
    }

    public Page<SlideInfo> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return slideInfoRepository.findByUserIdAndCreatedDateBetween(userId, startDateTime, endDateTime, pageable);
    }
    public Optional<SlideInfo> findByIdxAndOriginFileName(Long idx, String originFileName){
        return slideInfoRepository.findByIdxAndOriginFileName(idx, originFileName);
    }

    public Optional<SlideInfo> findByIdx(Long idx){
        return slideInfoRepository.findById(idx);
    }

    @SneakyThrows
    @Transactional
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

    public Resource downloadFile(Long idx) throws IOException {
        Optional<SlideInfo> slideInfoOptional = slideInfoRepository.findById(idx);
        Resource resource = null;
        if (slideInfoOptional.isPresent()) {
            SlideInfo slideInfo = slideInfoOptional.get();
            resource = getFileAsResource(slideInfo.getPath());
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
            log.error("IOException: ", e);
            throw new LunitExamException(ErrorCode.INTERNAL_SERVER_ERROR);
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
        Path dirPath = Paths.get(path);
        if(dirPath.toFile().exists()){
            return new UrlResource(dirPath.toUri());
        }
        return null;
    }
}
