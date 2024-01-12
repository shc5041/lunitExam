package com.example.lunitexam.repository;

import com.example.lunitexam.model.dao.SlideInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlideInfoRepositoryTest {

    @Autowired
    private SlideInfoRepository repository;

    @Test
    void saveTest(){
        SlideInfo slideInfo = SlideInfo.builder()
                .path("/adgsg/aasdgadgs")
                .originFileName("text.txt")
                .bytes(1l)
                .isUpload(true).build();

        SlideInfo insertInfo = repository.save(slideInfo);
        assertEquals(slideInfo.getOriginFileName(), insertInfo.getOriginFileName());

    }
}