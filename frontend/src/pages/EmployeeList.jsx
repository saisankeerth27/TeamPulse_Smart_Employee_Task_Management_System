import { useEffect, useState } from "react";
import { getAllEmployees, deleteEmployee } from "../services/employeeService";

function EmployeeList() {

    const [employees, setEmployees] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(() => {

        loadEmployees();

    }, []);

    const loadEmployees = async () => {

        try {

            const response = await getAllEmployees();

            setEmployees(response.data);

        } catch (error) {

            console.log(error);

        }

    };

    const removeEmployee = async (id) => {

        await deleteEmployee(id);

        loadEmployees();

    };

    const filteredEmployees = employees.filter(employee =>

        employee.firstName.toLowerCase().includes(search.toLowerCase()) ||

        employee.lastName.toLowerCase().includes(search.toLowerCase()) ||

        employee.email.toLowerCase().includes(search.toLowerCase())

    );

    return (

        <div>

            <h2>Employees</h2>

            <input

                placeholder="Search Employee"

                value={search}

                onChange={(e) => setSearch(e.target.value)}

            />

            <table border="1">

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Code</th>

                        <th>Name</th>

                        <th>Email</th>

                        <th>Designation</th>

                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        filteredEmployees.map(employee => (

                            <tr key={employee.id}>

                                <td>{employee.id}</td>

                                <td>{employee.employeeCode}</td>

                                <td>{employee.firstName} {employee.lastName}</td>

                                <td>{employee.email}</td>

                                <td>{employee.designation}</td>

                                <td>

                                    <button>Edit</button>

                                    <button
                                        onClick={() => removeEmployee(employee.id)}>

                                        Delete

                                    </button>

                                </td>

                            </tr>

                        ))

                    }

                </tbody>

            </table>

        </div>

    );

}

export default EmployeeList;
