package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.dto.ClassMemberDTO;
import com.ptit.EnglishExplorer.data.dto.CourseDTO;
import com.ptit.EnglishExplorer.data.dto.ExamDTO;
import com.ptit.EnglishExplorer.data.dto.UserDTO;
import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.AccessTypeCustom;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassroomDTO{
    private Long id;
    private AccessTypeCustom accessType;
    private String name;
    private String description;
    private boolean isMember = false;
    private int numberOfStudents = 10;
    private int numberOfLesstion = 10;
    private long cost = 0;
//    Set<ExamDTO> exams = new HashSet<>();
//    Set<ClassMemberDTO> classMembers = new HashSet<>();
    UserDTO user;
//    private Set<CourseDTO> courses = new HashSet<>();

    public ClassroomDTO(Classroom entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.accessType = entity.getAccessType();
        this.cost = entity.getCost();
        if(entity.getUser() != null) {
            this.user = new UserDTO(entity.getUser());
        }
//        if (entity.getExams() != null) {
//            for (Exam exam : entity.getExams()) {
//                this.exams.add(new ExamDTO(exam));
//            }
//        }
//        if(entity.getClassMembers() != null) {
//            for (ClassMember classMember : entity.getClassMembers()) {
//                this.classMembers.add(new ClassMemberDTO(classMember));
//            }
//        }
//        if(entity.getCourses() != null) {
//            for (Course course : entity.getCourses()) {
//                this.courses.add(new CourseDTO(course));
//            }
//        }
    }
}
