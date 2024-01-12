package com.example.lunitexam.service;

import com.example.lunitexam.model.dao.SlideInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlideInfoServiceTest {

    @Autowired
    private SlideInfoService slideInfoService;
    @Test
    void uploadFile() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello, World!".getBytes());

        boolean result = slideInfoService.uploadFile("test",file);

        assertEquals(true, result);

    }
    @Test
    void findByUserId() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<SlideInfo> slideInfoPage =  slideInfoService.findByUserId("test", pageable);
        assertEquals(slideInfoPage.getTotalElements(), 3);
    }

    @Test
    void findByUserIdAndOriginFileName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<SlideInfo> slideInfoPage =  slideInfoService.findByUserIdAndOriginFileName("test","CMU-1-JP2K-33005.svs", pageable);
        assertEquals(slideInfoPage.getTotalElements(), 1);
    }
}