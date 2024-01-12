package com.example.lunitexam.controller;

import com.example.lunitexam.model.dao.SlideInfo;
import com.example.lunitexam.service.SlideInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "1. Slide 파일 관련 API Controller", description = "Slide 파일 관련 API Controller")
@RequestMapping("/api/file")
public class SlideInfoController {

    private final SlideInfoService slideInfoService;

    /*파일 업로드, 업로드 결과 반환*/
    @Operation(description = "의료 사진을 upload 하며 파라미터로 userId값이 필요하며, userId 값은 아무값이나 넣으면 됩니다. ",operationId = "2")
    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> uploadFile(@PathVariable String userId, @RequestPart(value = "file") MultipartFile file) {
        return new ResponseEntity<>(slideInfoService.uploadFile(userId, file), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(description = "파일 upload된 data를 전체 가져오며, pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ",operationId = "1")
    public ResponseEntity<Page<SlideInfo>> findByUserIdAndOriginFileName(@ParameterObject @PageableDefault(sort = {"idx"}, value = 20) Pageable pageable) {
        return new ResponseEntity<>(slideInfoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(description = "특정 사용자가 upload 한 데이터를 가져옵니다. queryParam으로 " +
            "startDateTime, endDateTime을 줄 경우 범위 검색을 합니다.(필수 아님) " +
            "범위 검색시 format은 yyyy-MM-dd HH:mm:ss 이며, pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<SlideInfo>> findByUserId(@PathVariable String userId,
                                                        @Schema(defaultValue = "2024-01-01 00:00:00")
                                                        @RequestParam(value = "startDateTime", required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,

                                                        @Schema(defaultValue = "2024-12-31 23:59:59")
                                                        @RequestParam(value = "endDateTime", required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime,

                                                        @ParameterObject
                                                        @PageableDefault(sort = {"idx"}, value = 20) Pageable pageable) {
        if (startDateTime != null && endDateTime != null) {
            return new ResponseEntity<>(slideInfoService.findByUserIdAndCreatedDateBetween(userId, startDateTime, endDateTime, pageable), HttpStatus.OK);
        } else if (startDateTime == null && endDateTime != null) {
            //exception 처리
            return null;
        } else if (startDateTime != null && endDateTime == null) {
            //exception 처리
            return null;
        }
        return new ResponseEntity<>(slideInfoService.findByUserId(userId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{fileName}")
    @Operation(description = "upload한 userId와 fileName으로 검색을 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<SlideInfo>> findByUserIdAndOriginFileName(@PathVariable String userId, @PathVariable String fileName,
                                                                         @ParameterObject @PageableDefault(sort = {"idx"}, value = 20) Pageable pageable) {
        return new ResponseEntity<>(slideInfoService.findByUserIdAndOriginFileName(userId, fileName, pageable), HttpStatus.OK);
    }

    @GetMapping("/download/{idx}/{fileName}")
    @Operation(description = "upload한 index 값과 fileName으로 특정 파일을 다운로드 합니다. index 값은 현재 api 이외의 것에서 확인 할 수 있습니다.  ")
    public ResponseEntity<?> downloadFile(@PathVariable Long idx,@PathVariable String fileName) {
        Resource resource = null;
        try {
            resource = slideInfoService.downloadFile(idx, fileName);
            if (resource == null)
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
