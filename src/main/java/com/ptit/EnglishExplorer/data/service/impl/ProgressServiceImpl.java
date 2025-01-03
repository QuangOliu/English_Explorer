package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Progress;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.repository.ProgressRepository;
import com.ptit.EnglishExplorer.data.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressServiceImpl extends BaseServiceImpl<Progress, Long, ProgressRepository> implements ProgressService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository repository) {
        super(repository);
    }

    @Override
    public Progress markAsCompleted(Lesson lesson) {
        if (lesson.getId() != null) {
            lesson = lessonRepository.findById(lesson.getId()).orElse(null);
        }
        if (lesson == null || lesson.getId() == null) {
            return null; // Nếu lesson không tồn tại, trả về null
        }

        // Tìm kiếm user hiện tại
        User user = ApplicationAuditAware.getCurrentUser();

        if (user == null) {
            return null; // Nếu không tìm thấy user, trả về null
        }

        // Tìm kiếm progress của user và lesson
        Optional<Progress> progressOp = repository.findByUserAndLesson(user.getId(), lesson.getId());

        Progress progress;
        if (progressOp.isPresent()) {
            // Nếu progress đã tồn tại, lấy từ Optional
            progress = progressOp.get();
        } else {
            // Nếu không tồn tại progress, tạo mới
            progress = new Progress();
            progress.setUser(user);
            progress.setLesson(lesson);
        }

        // Đặt trạng thái là "hoàn thành"
        progress.setStatus(1);

        // Lưu progress vào cơ sở dữ liệu
        return repository.save(progress);
    }


}
