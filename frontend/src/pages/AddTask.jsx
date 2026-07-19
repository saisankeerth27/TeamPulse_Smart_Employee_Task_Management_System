import { useEffect, useState } from "react";

import { createTask } from "../services/taskService";
import { getDepartments } from "../services/departmentService";
import { getEmployeesByDepartment } from "../services/employeeService";

function AddTask() {

    const [departments, setDepartments] = useState([]);
    const [employees, setEmployees] = useState([]);

    const [task, setTask] = useState({

        title: "",

        description: "",

        priority: "MEDIUM",

        assignedEmployeeId: "",

        departmentId: "",

        dueDate: "",

        estimatedHours: ""

    });

    useEffect(() => {

        loadDepartments();

    }, []);

    const loadDepartments = async () => {

        const response = await getDepartments();

        setDepartments(response.data);

    };

    const handleDepartmentChange = async (e) => {

        const departmentId = e.target.value;

        setTask({

            ...task,

            departmentId

        });

        const response =
            await getEmployeesByDepartment(departmentId);

        setEmployees(response.data);

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        await createTask(task);

        alert("Task Created Successfully");

    };

    return (

        <form onSubmit={handleSubmit}>

            <div>

                <label>Title</label>

                <input

                    name="title"

                    value={task.title}

                    onChange={(e) =>

                        setTask({ ...task, title: e.target.value })

                    }

                />

            </div>

            <div>

                <label>Description</label>

                <textarea

                    name="description"

                    value={task.description}

                    onChange={(e) =>

                        setTask({ ...task, description: e.target.value })

                    }

                />

            </div>

            <div>

                <label>Priority</label>

                <select

                    name="priority"

                    value={task.priority}

                    onChange={(e) =>

                        setTask({ ...task, priority: e.target.value })

                    }

                >

                    <option value="LOW">Low</option>

                    <option value="MEDIUM">Medium</option>

                    <option value="HIGH">High</option>

                    <option value="CRITICAL">Critical</option>

                </select>

            </div>

            <div>

                <label>Department</label>

                <select

                    name="departmentId"

                    value={task.departmentId}

                    onChange={handleDepartmentChange}

                >

                    <option value="">Select Department</option>

                    {

                        departments.map(department => (

                            <option

                                key={department.id}

                                value={department.id}

                            >

                                {department.name}

                            </option>

                        ))

                    }

                </select>

            </div>

            <div>

                <label>Employee</label>

                <select

                    name="assignedEmployeeId"

                    value={task.assignedEmployeeId}

                    onChange={(e) =>

                        setTask({

                            ...task,

                            assignedEmployeeId: e.target.value

                        })

                    }

                >

                    <option value="">Select Employee</option>

                    {

                        employees.map(employee => (

                            <option

                                key={employee.id}

                                value={employee.id}

                            >

                                {employee.firstName}

                            </option>

                        ))

                    }

                </select>

            </div>

            <div>

                <label>Due Date</label>

                <input

                    type="date"

                    name="dueDate"

                    value={task.dueDate}

                    onChange={(e) =>

                        setTask({ ...task, dueDate: e.target.value })

                    }

                />

            </div>

            <div>

                <label>Estimated Hours</label>

                <input

                    type="number"

                    name="estimatedHours"

                    value={task.estimatedHours}

                    onChange={(e) =>

                        setTask({ ...task, estimatedHours: e.target.value })

                    }

                />

            </div>

            <button type="submit">Create Task</button>

        </form>

    );

}

export default AddTask;
