package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.dto.QuestionSearchDto;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.types.SkillType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionService extends CrudService<Question, Long> {
    public String saveImage(MultipartFile imageFile) throws IOException;
    public String saveAudio(MultipartFile audioFile) throws IOException;
    public List<Question> getQuestionsBySkill(SkillType skill);
    public List<Question> searchQuestions(QuestionSearchDto searchDto);
}
