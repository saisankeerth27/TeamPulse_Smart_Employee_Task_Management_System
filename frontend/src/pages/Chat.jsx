import { useEffect, useState } from "react";
import { connect, sendMessage, getConversation } from "../services/chatService";
import { getActiveEmployees } from "../services/employeeService";

function Chat() {

    const [employees, setEmployees] = useState([]);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const [messages, setMessages] = useState([]);
    const [text, setText] = useState("");
    const loggedInUserId = localStorage.getItem("id");

    useEffect(() => {

        const loadEmployees = async () => {

            const response = await getActiveEmployees();

            setEmployees(response.data);

        };

        loadEmployees();

    }, []);

    useEffect(() => {

        if (loggedInUserId) {

            connect(loggedInUserId, (message) => {

                setMessages(previous => [...previous, message]);

            });

        }

    }, []);

    const openConversation = async (employee) => {

        setSelectedEmployee(employee);

        const response = await getConversation(
            loggedInUserId,
            employee.id
        );

        setMessages(response.data);

    };

    const handleSend = async () => {

        if (!selectedEmployee || !text.trim()) return;

        const messagePayload = {

            senderId: parseInt(loggedInUserId),
            receiverId: selectedEmployee.id,
            message: text

        };

        sendMessage(messagePayload);

        setText("");

    };

    const currentUserId = parseInt(loggedInUserId);

    return (

        <div style={{ display: "flex", height: "80vh" }}>

            <div style={{ width: "250px", borderRight: "1px solid #ccc", padding: "10px" }}>

                <h3>Employees</h3>

                {

                    employees
                        .filter(emp => emp.id !== currentUserId)
                        .map(employee => (

                            <div
                                key={employee.id}
                                onClick={() => openConversation(employee)}
                                style={{
                                    padding: "10px",
                                    cursor: "pointer",
                                    background: selectedEmployee?.id === employee.id ? "#e0e0e0" : "transparent"
                                }}
                            >

                                {employee.firstName} {employee.lastName}

                            </div>

                        ))

                }

            </div>

            <div style={{ flex: 1, display: "flex", flexDirection: "column", padding: "10px" }}>

                <h3>

                    {selectedEmployee
                        ? selectedEmployee.firstName + " " + selectedEmployee.lastName
                        : "Select a person to chat"
                    }

                </h3>

                <div style={{ flex: 1, overflowY: "auto", border: "1px solid #ccc", padding: "10px", marginBottom: "10px" }}>

                    {

                        messages.map((message, index) => (

                            <div key={index} style={{ marginBottom: "8px" }}>

                                <strong>{message.sender}</strong>: {message.message}

                            </div>

                        ))

                    }

                </div>

                <div style={{ display: "flex", gap: "10px" }}>

                    <input

                        style={{ flex: 1 }}

                        value={text}

                        onChange={(e) => setText(e.target.value)}

                        onKeyDown={(e) => e.key === "Enter" && handleSend()}

                        disabled={!selectedEmployee}

                    />

                    <button onClick={handleSend} disabled={!selectedEmployee}>

                        Send

                    </button>

                </div>

            </div>

        </div>

    );

}

export default Chat;
