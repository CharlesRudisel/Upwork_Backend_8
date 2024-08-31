package com.example.Upwork_Backend_8.assignments.service;

import com.example.Upwork_Backend_8.assignments.entity.Assignment;
import com.example.Upwork_Backend_8.assignments.repository.AssignmentRepository;
import com.example.Upwork_Backend_8.users.entity.UserInfo;
import com.example.Upwork_Backend_8.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;
    
    public String updateAssignmentStatus(Long assignmentId, String status, Long userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        Optional<UserInfo> userOptional = userRepository.findById(userId);

        if (assignmentOptional.isPresent() && userOptional.isPresent()) {
            Assignment assignment = assignmentOptional.get();
            UserInfo user = userOptional.get();

            assignment.setStatus(status);
            assignment.setUser(user);

            assignmentRepository.save(assignment);
            return "Assignment status updated successfully";
        } else {
            return "Assignment or User not found";
        }
    }

    public List<Assignment> getAssignmentsByUserId(Long userId) {
        return assignmentRepository.findByUserId(userId);
    }


    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> saveAllAssignments(List<Assignment> assignments) {
        return assignmentRepository.saveAll(assignments);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
