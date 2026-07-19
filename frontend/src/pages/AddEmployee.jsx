import { useState } from "react";
import { createEmployee } from "../services/employeeService";

function AddEmployee() {

    const [employee, setEmployee] = useState({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        designation: "",
        salary: "",
        joiningDate: ""
    });

    const handleChange = (e) => {
        setEmployee({
            ...employee,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await createEmployee(employee);
            alert("Employee Added Successfully");
        } catch (error) {
            alert("Failed to Add Employee");
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="firstName" placeholder="First Name" onChange={handleChange} />
            <input name="lastName" placeholder="Last Name" onChange={handleChange} />
            <input name="email" placeholder="Email" onChange={handleChange} />
            <input name="phone" placeholder="Phone" onChange={handleChange} />
            <input name="designation" placeholder="Designation" onChange={handleChange} />
            <input name="salary" type="number" placeholder="Salary" onChange={handleChange} />
            <input name="joiningDate" type="date" onChange={handleChange} />

            <button type="submit">
                Add Employee
            </button>
        </form>
    );
}

export default AddEmployee;
