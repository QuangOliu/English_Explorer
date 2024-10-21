package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.repository.ChoiseRepository;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.repository.NotificationRepository;
import com.ptit.EnglishExplorer.data.repository.QuestionRepository;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import com.ptit.EnglishExplorer.data.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, Long, QuestionRepository> implements QuestionService {

    private final String IMAGE_UPLOAD_DIR = "static/images/"; // Define the path for image uploads
    private final String AUDIO_UPLOAD_DIR = "static/audio/";  // Define the path for audio uploads

    @Autowired
    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LessonRepository lessonRepository; // Nếu bạn cần tìm bài học

    @Autowired
    private ChoiseRepository choiseRepository;

    @Override
    @Transactional
    public Question save(Question entity) {
        if (entity.getLesson() != null) {
            Lesson lesson = lessonRepository.findById(entity.getLesson().getId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            entity.setLesson(lesson);
        }

        Question question;
        if (entity.getId() == null) {
            // Tạo mới câu hỏi
            question = new Question();
        } else {
            // Cập nhật câu hỏi hiện có
            question = questionRepository.findById(entity.getId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
        }

        // Cập nhật thuộc tính câu hỏi
        question.setLesson(entity.getLesson());
        question.setImage(entity.getImage());
        question.setAudio(entity.getAudio());
        question.setSkill(entity.getSkill());
        question.setLevel(entity.getLevel());
        question.setQuestion(entity.getQuestion());

        // Xử lý các lựa chọn
        if (entity.getChoises() != null) {
            question.getChoises().clear(); // Xóa các lựa chọn cũ

            for (Choise choiseDTO : entity.getChoises()) {
                // Kiểm tra số lượng lựa chọn trước khi thêm
//                if (question.getChoises().size() < 4) {
                Choise choise = new Choise();
                choise.setAnswer(choiseDTO.getAnswer());
                choise.setCorrect(choiseDTO.isCorrect());
                choise.setQuestion(question); // Thiết lập câu hỏi cho lựa chọn
                question.getChoises().add(choise); // Thêm lựa chọn vào câu hỏi
//                } else {
//                    throw new RuntimeException("A question can only have up to 4 choices.");
//                }
            }
        }

        return questionRepository.save(question); // Lưu câu hỏi
    }

    /**
     * Saves the image to the specified directory.
     *
     * @param imageFile The image file to be saved.
     * @return The path of the saved image file.
     * @throws IOException If there is an error saving the file.
     */
    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }

        // Create directory if it doesn't exist
        Path directory = Paths.get("src/main/resources/" + IMAGE_UPLOAD_DIR);
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        // Save the file
        String fileName = imageFile.getOriginalFilename();
        Path path = directory.resolve(fileName);
        Files.copy(imageFile.getInputStream(), path); // Save the image

        return path.toString(); // Return the saved image path
    }

    /**
     * Saves the audio to the specified directory.
     *
     * @param audioFile The audio file to be saved.
     * @return The path of the saved audio file.
     * @throws IOException If there is an error saving the file.
     */
    public String saveAudio(MultipartFile audioFile) throws IOException {
        if (audioFile.isEmpty()) {
            throw new IllegalArgumentException("Audio file is empty");
        }

        // Define the directory where the audio will be saved
        String AUDIO_UPLOAD_DIR = "static/audio"; // Assuming this is the directory inside src/main/resources
        Path directory = Paths.get("src/main/resources/" + AUDIO_UPLOAD_DIR);

        // Create the directory if it doesn't exist
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        // Extract the original file name
        String originalFileName = audioFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("Audio file name is invalid");
        }

        // Sanitize the file name: replace spaces with underscores or hyphens
        String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");

        // Define the destination path
        Path destinationPath = directory.resolve(sanitizedFileName);

        // If the file already exists, rename it by appending the current timestamp
        if (Files.exists(destinationPath)) {
            String newFileName = sanitizedFileName.replace(".mp3", "") + "_" + System.currentTimeMillis() + ".mp3";
            destinationPath = directory.resolve(newFileName);
        }

        // Save the audio file
        Files.copy(audioFile.getInputStream(), destinationPath);

        // Return the saved audio path (relative to the static directory)
        return destinationPath.toString().replace("\\", "/").replace("src/main/resources/", "");
    }

}