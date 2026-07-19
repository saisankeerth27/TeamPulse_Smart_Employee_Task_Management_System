package com.teampulse.backend.service;

import com.teampulse.backend.dto.CommentRequest;
import com.teampulse.backend.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(CommentRequest request);

    List<CommentResponse> getCommentsByTask(Long taskId);

    void deleteComment(Long id);

}
