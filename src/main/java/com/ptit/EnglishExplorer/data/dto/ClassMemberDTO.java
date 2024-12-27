package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.ClassMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMemberDTO {
    private Long id;
    private UserDTO user;
    private ClassroomDTO classroom;

    // Constructor từ ClassMember entity
    public ClassMemberDTO(ClassMember classMember) {
        this.id = classMember.getId();
        if(classMember.getUser()!=null) {
            this.user = new UserDTO(classMember.getUser());
        }
        if(classMember.getUser()!=null) {
            this.classroom = new ClassroomDTO(classMember.getClassroom());
        }
    }
}
