package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.ClassroomDTO;
import com.ptit.EnglishExplorer.data.entity.*;
import com.ptit.EnglishExplorer.data.repository.ClassMemberRepository;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.TransactionRepository;
import com.ptit.EnglishExplorer.data.repository.WalletRepository;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.types.TransactionStatus;
import com.ptit.EnglishExplorer.utils.RoleUtils;
import com.ptit.EnglishExplorer.vnpay.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl extends BaseServiceImpl<Classroom, Long, ClassroomRepository> implements ClassroomService {

    @Autowired
    private ClassMemberRepository classMemberRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository repository) {
        super(repository);
    }


    @Override
    public Classroom save(Classroom entity) {
        Classroom newEntity = null;
        if (entity.getId() != null) {
            newEntity = repository.findById(entity.getId()).get();
        }
        if (newEntity == null) {
            newEntity = new Classroom();
            User user = ApplicationAuditAware.getCurrentUser();
            if (user != null) {
                newEntity.setUser(user);
            }
        }
        newEntity.setName(entity.getName());
        newEntity.setDescription(entity.getDescription());
        newEntity.setAccessType(entity.getAccessType());
        newEntity.setCost(entity.getCost());

        return repository.save(entity);
    }


    @Override
    public Page<ClassroomDTO> findListClassroomDTO(Pageable pageable) {
        // Fetch the paginated Classroom entities
        Page<Classroom> classroomsPage = repository.findAll(pageable);

        // Manually convert Classroom entities to ClassroomDTO objects
        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        User user = ApplicationAuditAware.getCurrentUser();
        for (Classroom classroom : classroomsPage.getContent()) {
            ClassroomDTO classroomDTO = new ClassroomDTO(classroom);
            // Check if the user is a member of the classroom using the custom query
            Optional<ClassMember> classMember = null;
            if (user != null && user.getId() != null) {
                classMember = classMemberRepository.findByUserIdAndClassroomId(user.getId(), classroom.getId());
            }

            /* Set the isMember flag in the DTO */
            if (classMember != null) {
                classroomDTO.setMember(classMember.isPresent());
            }

            classroomDTOList.add(classroomDTO);
        }

        // Convert the List<ClassroomDTO> to a Page<ClassroomDTO>

        return new PageImpl<>(classroomDTOList, pageable, classroomsPage.getTotalElements());
    }

    @Override
    @Transactional
    public ResponseObject<?> buyById(Long id) {
        try {
            // Fetch the classroom using the provided ID
            Classroom classroom = repository.findById(id).orElse(null);

            // If the classroom doesn't exist, return an appropriate response
            if (classroom == null) {
                return new ResponseObject<>(HttpStatus.NOT_FOUND, "Classroom not found", null);
            }

            // Fetch the current user
            User user = ApplicationAuditAware.getCurrentUser();
            if (user == null) {
                return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "User is not authenticated", null);
            }

            // Fetch the user's wallet
            Wallet wallet = walletRepository.findWalletByUserId(user.getId());

            // If wallet doesn't exist, create a new one and save it
            if (wallet == null) {
                wallet = new Wallet();
                wallet.setUser(user);
                walletRepository.save(wallet);
            }

            // Check if the wallet balance is sufficient for the classroom cost
            if (wallet.getBalance() >= classroom.getCost()) {
                // Proceed with the transaction
                Transaction transaction = new Transaction();
                transaction.setWallet(wallet);
                transaction.setUser(user);
                transaction.setAmount(-classroom.getCost());  // Deduct the cost from wallet balance
                transaction.setStatus(TransactionStatus.SUCCESS);
                String timestamp = String.valueOf(System.currentTimeMillis());
                transaction.setRef(timestamp);
                transactionRepository.save(transaction); // Save the transaction to the database

                // Update the wallet balance
                wallet.setBalance(wallet.getBalance() - classroom.getCost());
                walletRepository.save(wallet); // Save updated wallet

                ClassMember classMember = new ClassMember();
                classMember.setUser(user);
                classMember.setClassroom(classroom);
                classMemberRepository.save(classMember);

                // Return a success response with the classroom details
                return new ResponseObject<>(HttpStatus.OK, "Classroom purchased successfully", classroom);
            } else {
                // Return failure response if balance is insufficient
                return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Insufficient balance", null);
            }
        } catch (Exception e) {
            // Return error response if an exception occurs
            return new ResponseObject<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), null);
        }
    }

    @Override
    public Page<Classroom> findMyOwnClassroom(Pageable pageable) {
        User user = ApplicationAuditAware.getCurrentUser();

        if(RoleUtils.isAdmin(user)) {
            return this.findList(pageable);
        }
        if (RoleUtils.isTeacher(user)) {
            // Nếu người dùng là Teacher, tìm tất cả lớp học của giáo viên đó
            return repository.findByTeacher(user.getUsername(), pageable);  // Giả sử có phương thức này trong repository
        }

        return null;  // Trường hợp không phải Admin hoặc Teacher, trả về null
    }

}
