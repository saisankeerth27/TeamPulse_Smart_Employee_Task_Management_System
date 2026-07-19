import api from "./api";

export const getNotifications = (id) =>
    api.get(`/notifications/${id}`);

export const unreadCount = (id) =>
    api.get(`/notifications/${id}/count`);

export const markRead = (id) =>
    api.patch(`/notifications/${id}`);
