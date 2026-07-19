import { useEffect, useState } from "react";
import { getTasks, updateTaskStatus, deleteTask } from "../services/taskService";
import { getCommentsByTask, createComment, deleteComment } from "../services/commentService";

function TaskList() {

    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const loggedInUserId = localStorage.getItem("id");

    useEffect(() => {

        loadTasks();

    }, []);

    const loadTasks = async () => {

        const response = await getTasks();

        setTasks(response.data);

    };

    const openTask = async (task) => {

        setSelectedTask(task);

        const response = await getCommentsByTask(task.id);

        setComments(response.data);

    };

    const handleAddComment = async () => {

        if (!newComment.trim()) return;

        await createComment({

            message: newComment,
            taskId: selectedTask.id,
            employeeId: parseInt(loggedInUserId)

        });

        setNewComment("");

        const response = await getCommentsByTask(selectedTask.id);

        setComments(response.data);

    };

    const handleDeleteComment = async (id) => {

        await deleteComment(id);

        const response = await getCommentsByTask(selectedTask.id);

        setComments(response.data);

    };

    const handleStatusChange = async (id, status) => {

        await updateTaskStatus(id, status);

        loadTasks();

    };

    const handleDeleteTask = async (id) => {

        await deleteTask(id);

        loadTasks();

        if (selectedTask?.id === id) {

            setSelectedTask(null);

        }

    };

    const timeAgo = (dateStr) => {

        const now = new Date();
        const date = new Date(dateStr);
        const seconds = Math.floor((now - date) / 1000);

        if (seconds < 60) return seconds + "s ago";
        const minutes = Math.floor(seconds / 60);
        if (minutes < 60) return minutes + "m ago";
        const hours = Math.floor(minutes / 60);
        if (hours < 24) return hours + "h ago";
        const days = Math.floor(hours / 24);
        return days + "d ago";

    };

    const statusColors = {
        TODO: "#ff9800",
        IN_PROGRESS: "#2196f3",
        COMPLETED: "#4caf50",
        BLOCKED: "#f44336"
    };

    const priorityColors = {
        LOW: "#8bc34a",
        MEDIUM: "#ff9800",
        HIGH: "#f44336",
        CRITICAL: "#9c27b0"
    };

    return (

        <div style={{ display: "flex", height: "80vh" }}>

            <div style={{ width: "40%", borderRight: "1px solid #ccc", padding: "10px", overflowY: "auto" }}>

                <h2>Tasks</h2>

                {tasks.map(task => (

                    <div
                        key={task.id}
                        onClick={() => openTask(task)}
                        style={{
                            padding: "10px",
                            margin: "5px 0",
                            cursor: "pointer",
                            borderRadius: "4px",
                            background: selectedTask?.id === task.id ? "#e3f2fd" : "#f9f9f9"
                        }}
                    >

                        <strong>{task.title}</strong>

                        <div style={{ fontSize: "12px", marginTop: "5px" }}>

                            <span style={{
                                background: priorityColors[task.priority] || "#888",
                                color: "#fff",
                                padding: "2px 6px",
                                borderRadius: "4px",
                                marginRight: "5px"
                            }}>
                                {task.priority}
                            </span>

                            <span style={{
                                background: statusColors[task.status] || "#888",
                                color: "#fff",
                                padding: "2px 6px",
                                borderRadius: "4px"
                            }}>
                                {task.status}
                            </span>

                        </div>

                        <div style={{ fontSize: "12px", color: "#666", marginTop: "5px" }}>
                            {task.assignedEmployee} | {task.department}
                        </div>

                    </div>

                ))}

            </div>

            <div style={{ flex: 1, padding: "10px", overflowY: "auto" }}>

                {selectedTask ? (

                    <div>

                        <h2>{selectedTask.title}</h2>

                        <p>{selectedTask.description}</p>

                        <div style={{ marginBottom: "10px" }}>

                            <select
                                value={selectedTask.status}
                                onChange={(e) =>
                                    handleStatusChange(
                                        selectedTask.id,
                                        e.target.value
                                    )
                                }
                                style={{ marginRight: "10px" }}
                            >
                                <option value="TODO">TODO</option>
                                <option value="IN_PROGRESS">IN PROGRESS</option>
                                <option value="COMPLETED">COMPLETED</option>
                                <option value="BLOCKED">BLOCKED</option>
                            </select>

                            <button
                                onClick={() =>
                                    handleDeleteTask(selectedTask.id)
                                }
                                style={{ color: "red" }}
                            >
                                Delete
                            </button>

                        </div>

                        <h3>Comments</h3>

                        <div style={{ marginBottom: "10px" }}>

                            {comments.map(comment => (

                                <div
                                    key={comment.id}
                                    style={{
                                        padding: "8px",
                                        margin: "5px 0",
                                        background: "#f5f5f5",
                                        borderRadius: "4px"
                                    }}
                                >

                                    <strong>{comment.employeeName}</strong>

                                    <span style={{
                                        fontSize: "11px",
                                        color: "#888",
                                        marginLeft: "10px"
                                    }}>
                                        {timeAgo(comment.createdAt)}
                                    </span>

                                    <p style={{ margin: "5px 0" }}>
                                        {comment.message}
                                    </p>

                                    <button
                                        onClick={() =>
                                            handleDeleteComment(comment.id)
                                        }
                                        style={{
                                            fontSize: "11px",
                                            color: "red",
                                            cursor: "pointer"
                                        }}
                                    >
                                        Delete
                                    </button>

                                </div>

                            ))}

                        </div>

                        <div style={{ display: "flex", gap: "10px" }}>

                            <input
                                style={{ flex: 1 }}
                                value={newComment}
                                onChange={(e) =>
                                    setNewComment(e.target.value)
                                }
                                onKeyDown={(e) =>
                                    e.key === "Enter" && handleAddComment()
                                }
                                placeholder="Write a comment..."
                            />

                            <button onClick={handleAddComment}>
                                Comment
                            </button>

                        </div>

                    </div>

                ) : (

                    <div style={{ padding: "20px", color: "#888" }}>

                        Select a task to view details and comments

                    </div>

                )}

            </div>

        </div>

    );

}

export default TaskList;
