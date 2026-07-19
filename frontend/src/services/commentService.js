import api from "./api";

export const getCommentsByTask = (taskId) =>
    api.get(`/comments/task/${taskId}`);

export const createComment = (comment) =>
    api.post("/comments", comment);

export const deleteComment = (id) =>
    api.delete(`/comments/${id}`);
