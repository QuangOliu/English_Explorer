package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QuestionService extends CrudService<Question, Long> {
    public String saveImage(MultipartFile imageFile) throws IOException;
    public String saveAudio(MultipartFile audioFile) throws IOException;
}
