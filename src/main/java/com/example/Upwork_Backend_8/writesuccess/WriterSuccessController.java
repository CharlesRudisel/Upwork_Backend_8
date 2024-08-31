package com.example.Upwork_Backend_8.writesuccess;


import com.example.Upwork_Backend_8.writesuccess.entity.WriterSuccess;
import com.example.Upwork_Backend_8.writesuccess.services.WriterSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/writerSuccesses")
public class WriterSuccessController {

    @Autowired
    private WriterSuccessService writerSuccessService;

    @GetMapping
    public ResponseEntity<List<WriterSuccess>> getAllWriterSuccesses() {
        return ResponseEntity.ok(writerSuccessService.getAllWriterSuccesses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriterSuccess> getWriterSuccessById(@PathVariable Long id) {
        Optional<WriterSuccess> writerSuccess = writerSuccessService.getWriterSuccessById(id);
        return writerSuccess.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WriterSuccess> createWriterSuccess(@RequestBody WriterSuccess writerSuccess) {
        return ResponseEntity.ok(writerSuccessService.saveWriterSuccess(writerSuccess));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<WriterSuccess>> createWriterSuccesses(@RequestBody List<WriterSuccess> writerSuccesses) {
        List<WriterSuccess> createdWriterSuccesses = writerSuccessService.saveAllWriterSuccesses(writerSuccesses);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWriterSuccesses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WriterSuccess> updateWriterSuccess(@PathVariable Long id, @RequestBody WriterSuccess writerSuccessDetails) {
        Optional<WriterSuccess> writerSuccessOptional = writerSuccessService.getWriterSuccessById(id);

        if (!writerSuccessOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        WriterSuccess writerSuccess = writerSuccessOptional.get();
        writerSuccess.setMetrics(writerSuccessDetails.getMetrics());
        writerSuccess.setComments(writerSuccessDetails.getComments());
        WriterSuccess updatedWriterSuccess = writerSuccessService.saveWriterSuccess(writerSuccess);
        return ResponseEntity.ok(updatedWriterSuccess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWriterSuccess(@PathVariable Long id) {
        if (!writerSuccessService.getWriterSuccessById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        writerSuccessService.deleteWriterSuccess(id);
        return ResponseEntity.noContent().build();
    }
}

