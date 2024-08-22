package com.example.Upwork_Backend_8.assignments;


import com.example.Upwork_Backend_8.assignments.entity.Assignment;
import com.example.Upwork_Backend_8.assignments.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PutMapping("/available/{assignmentId}/status/{status}/{userId}")
    public ResponseEntity<String> updateAssignmentStatus(@PathVariable Long assignmentId, @PathVariable String status, @PathVariable Long userId) {
        String result = assignmentService.updateAssignmentStatus(assignmentId, status, userId);
        if (result.equals("Assignment status updated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByUserId(@PathVariable Long userId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(userId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Assignment>> getAllAvailableAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        // Filter assignments to include only those with status 'available'
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> "available".equalsIgnoreCase(assignment.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }

    @GetMapping("/user/progress/{userId}")
    public ResponseEntity<List<Assignment>> getInProgressAssignmentsByUserId(@PathVariable Long userId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(userId); // Gets assignments by userId
        // Filter assignments to include only those with status 'In Progress'
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> "In Progress".equalsIgnoreCase(assignment.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }


    @GetMapping("/user/available/{userId}")
    public ResponseEntity<List<Assignment>> getAvailableAssignmentsByUserId(@PathVariable Long userId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(userId);
        // Filter assignments to include only those with status 'available'
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> "available".equalsIgnoreCase(assignment.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }

    @GetMapping("/user/pending/{userId}")
    public ResponseEntity<List<Assignment>> getPendingAssignmentsByUserId(@PathVariable Long userId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(userId);
        // Filter assignments to include only those with status 'available'
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> "pending".equalsIgnoreCase(assignment.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }

    @GetMapping("/user/completed/{userId}")
    public ResponseEntity<List<Assignment>> getCompletedAssignmentsByUserId(@PathVariable Long userId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(userId);
        // Filter assignments to include only those with status 'available'
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> "completed".equalsIgnoreCase(assignment.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        return assignment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.saveAssignment(assignment));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Assignment>> createAssignments(@RequestBody List<Assignment> assignments) {
        List<Assignment> createdAssignments = assignmentService.saveAllAssignments(assignments);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignmentDetails) {
        Optional<Assignment> assignmentOptional = assignmentService.getAssignmentById(id);

        if (!assignmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Assignment assignment = assignmentOptional.get();
        assignment.setClientName(assignmentDetails.getClientName());
        assignment.setBlogTopic(assignmentDetails.getBlogTopic());
        assignment.setDueDate(assignmentDetails.getDueDate());
        assignment.setStatus(assignmentDetails.getStatus());
        Assignment updatedAssignment = assignmentService.saveAssignment(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        if (!assignmentService.getAssignmentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}

