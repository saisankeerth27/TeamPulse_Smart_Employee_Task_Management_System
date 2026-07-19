import { useEffect, useState } from "react";
import { getDashboard } from "../services/dashboardService";
import { getNotifications, unreadCount, markRead } from "../services/notificationService";
import { useNavigate } from "react-router-dom";

function Dashboard() {

    const navigate = useNavigate();
    const [dashboard, setDashboard] = useState({});
    const [notifications, setNotifications] = useState([]);
    const [count, setCount] = useState(0);
    const [showNotifications, setShowNotifications] = useState(false);
    const loggedInUserId = localStorage.getItem("id");

    useEffect(() => {

        loadDashboard();

        if (loggedInUserId) {

            loadNotifications();

        }

    }, []);

    const loadDashboard = async () => {

        const response = await getDashboard();

        setDashboard(response.data);

    };

    const loadNotifications = async () => {

        const countRes = await unreadCount(loggedInUserId);

        setCount(countRes.data);

        const notifRes = await getNotifications(loggedInUserId);

        setNotifications(notifRes.data);

    };

    const handleMarkRead = async (id) => {

        await markRead(id);

        loadNotifications();

    };

    const logout = () => {

        localStorage.clear();

        navigate("/");

    };

    return (

        <div>

            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>

                <div>

                    <h1>Welcome {localStorage.getItem("name")}</h1>

                    <h3>Role: {localStorage.getItem("role")}</h3>

                </div>

                <div style={{ display: "flex", gap: "15px", alignItems: "center" }}>

                    <div style={{ position: "relative" }}>

                        <button

                            onClick={() => setShowNotifications(!showNotifications)}

                            style={{ fontSize: "20px", cursor: "pointer" }}

                        >

                            🔔 {count > 0 && <span>({count})</span>}

                        </button>

                        {showNotifications && (

                            <div style={{

                                position: "absolute",
                                right: 0,
                                top: "35px",
                                width: "350px",
                                maxHeight: "400px",
                                overflowY: "auto",
                                border: "1px solid #ccc",
                                background: "#fff",
                                borderRadius: "8px",
                                padding: "10px",
                                zIndex: 1000,
                                boxShadow: "0 4px 8px rgba(0,0,0,0.1)"

                            }}>

                                <h4>Notifications</h4>

                                {notifications.length === 0 ? (

                                    <p>No notifications</p>

                                ) : (

                                    notifications.map(notif => (

                                        <div

                                            key={notif.id}

                                            onClick={() => handleMarkRead(notif.id)}

                                            style={{

                                                padding: "8px",
                                                margin: "5px 0",
                                                borderRadius: "4px",
                                                cursor: "pointer",
                                                background: notif.isRead ? "#f9f9f9" : "#e3f2fd"

                                            }}

                                        >

                                            <strong>{notif.title}</strong>

                                            <p style={{ margin: 0, fontSize: "13px" }}>{notif.message}</p>

                                            <small style={{ color: "#888" }}>

                                                {new Date(notif.createdAt).toLocaleString()}

                                            </small>

                                        </div>

                                    ))

                                )}

                            </div>

                        )}

                    </div>

                    <button onClick={logout}>Logout</button>

                </div>

            </div>

            <div className="cards" style={{ display: "flex", gap: "20px", flexWrap: "wrap", marginTop: "20px" }}>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Employees
                    <h3>{dashboard.totalEmployees}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Departments
                    <h3>{dashboard.totalDepartments}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Tasks
                    <h3>{dashboard.totalTasks}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Completed
                    <h3>{dashboard.completedTasks}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    In Progress
                    <h3>{dashboard.inProgressTasks}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Todo
                    <h3>{dashboard.todoTasks}</h3>
                </div>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Blocked
                    <h3>{dashboard.blockedTasks}</h3>
                </div>

            </div>

        </div>

    );

}

export default Dashboard;
