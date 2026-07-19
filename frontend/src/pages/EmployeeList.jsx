import { useEffect, useState } from "react";
import { getAllEmployees } from "../services/employeeService";

function EmployeeList() {

    const [employees, setEmployees] = useState([]);

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

    return (

        <div>

            <h2>Employees</h2>

            <table border="1">

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Code</th>

                        <th>Name</th>

                        <th>Email</th>

                        <th>Designation</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        employees.map(employee => (

                            <tr key={employee.id}>

                                <td>{employee.id}</td>

                                <td>{employee.employeeCode}</td>

                                <td>{employee.firstName} {employee.lastName}</td>

                                <td>{employee.email}</td>

                                <td>{employee.designation}</td>

                            </tr>

                        ))

                    }

                </tbody>

            </table>

        </div>

    );

}

export default EmployeeList;
