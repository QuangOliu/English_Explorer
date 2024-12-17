package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import com.ptit.EnglishExplorer.data.repository.ExamRepository;
import com.ptit.EnglishExplorer.data.repository.UserExamRepository;
import com.ptit.EnglishExplorer.data.service.ClassMemberService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import com.ptit.EnglishExplorer.data.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserExamServiceImpl extends BaseServiceImpl<UserExam, Long, UserExamRepository> implements UserExamService {

    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    public UserExamServiceImpl(UserExamRepository repository) {
        super(repository);
    }

    @Override
    public List<UserExam> getAllUserExamInExams(Long id) {
        Optional<Exam> exam = examRepository.findById(id);

        if (!exam.isPresent()) {
            return null;
        }

        List<UserExam> result = new ArrayList<>();

        List<UserExam> listUserExam = repository.getByExam(id);
        result.addAll(listUserExam);

        List<User> listUser = classMemberService.getAllUserByClassroom(exam.get().getClassroom().getId());
        for (User user : listUser) {
            // những user nào chưc co trong result thi thêm vao con user nafo co trong roi thi bo qua
            if (!checkUserInExam(user, result)) {
                UserExam userExam = new UserExam();
                userExam.setUser(user);
                userExam.setExam(exam.get());
                userExam.setScore(null);
                result.add(userExam);
            }
        }
        return result;
    }

    private boolean checkUserInExam(User user, List<UserExam> listUserExam) {
        for (UserExam userExam : listUserExam) {
            if (Objects.equals(userExam.getUser().getId(), user.getId())) {
                return true;
            }
        }
        return false;
    }
}
