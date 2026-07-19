package com.teampulse.backend.controller;

import com.teampulse.backend.dto.CommentRequest;
import com.teampulse.backend.dto.CommentResponse;
import com.teampulse.backend.service.CommentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public CommentResponse createComment(
            @Valid @RequestBody CommentRequest request) {
        return service.createComment(request);
    }

    @GetMapping("/task/{taskId}")
    public List<CommentResponse> getCommentsByTask(
            @PathVariable Long taskId) {
        return service.getCommentsByTask(taskId);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return "Comment Deleted Successfully";
    }

}
