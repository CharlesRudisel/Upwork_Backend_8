package com.example.Upwork_Backend_8.assignments;


import com.example.Upwork_Backend_8.assignments.entity.Assignment;
import com.example.Upwork_Backend_8.assignments.repository.AssignmentRepository;
import com.example.Upwork_Backend_8.assignments.service.AssignmentService;
import com.example.Upwork_Backend_8.assignments.specification.AssignmentSpecification;
import com.example.Upwork_Backend_8.assignments.specification.SearchCriteria;
import com.example.Upwork_Backend_8.assignments.specification.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private AssignmentRepository assignmentRepository;

    @PutMapping("/available/{assignmentId}/status/{status}/{userId}")
    public ResponseEntity<String> updateAssignmentStatus(@PathVariable Long assignmentId, @PathVariable String status, @PathVariable Long userId) {
        String result = assignmentService.updateAssignmentStatus(assignmentId, status, userId);
        System.out.println("result : "+result);
        if (result.equals("Assignment status updated successfully")) {
            System.out.println("redewewq : "+result);
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
        // Filter assignments to include only those with status 'In Progress', regardless of spaces
        List<Assignment> availableAssignments = assignments.stream()
                .filter(assignment -> normalizeStatus(assignment.getStatus()).equalsIgnoreCase("inprogress"))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableAssignments);
    }

    // Helper method to normalize status by removing spaces and converting to lowercase
    private String normalizeStatus(String status) {
        if (status == null) {
            return "";
        }
        return status.replaceAll("\\s+", "").toLowerCase();
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

    @GetMapping("/filtered/completed")
    public Page<Assignment> getFilteredCompletedAssignments(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String date,
            Pageable pageable
    ){
        AssignmentSpecification specs = new AssignmentSpecification();

        if(searchQuery != null && !searchQuery.isEmpty()){

            specs.addCriteria(new SearchCriteria("blogTopic" , SearchOperation.LIKE , searchQuery));
        }
        if(clientName != null && !clientName.isEmpty()){

        }
        if(author != null && !author.isEmpty()){

        }
        if(date != null && !date.isEmpty()){

        }

        return assignmentRepository.findAll(specs,pageable);
    }
}
