import { useEffect, useState } from "react";
import { getDepartments } from "../services/departmentService";

function DepartmentList() {

    const [departments, setDepartments] = useState([]);

    useEffect(() => {

        loadDepartments();

    }, []);

    const loadDepartments = async () => {

        const response = await getDepartments();

        setDepartments(response.data);

    };

    return (

        <div>

            <h2>Departments</h2>

            <table border="1">

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Name</th>

                        <th>Description</th>

                        <th>Employees</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        departments.map(department => (

                            <tr key={department.id}>

                                <td>{department.id}</td>

                                <td>{department.name}</td>

                                <td>{department.description}</td>

                                <td>{department.employeeCount}</td>

                            </tr>

                        ))

                    }

                </tbody>

            </table>

        </div>

    );

}

export default DepartmentList;
