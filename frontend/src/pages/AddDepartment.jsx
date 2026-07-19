import { useState } from "react";
import { createDepartment } from "../services/departmentService";

function AddDepartment() {

    const [department, setDepartment] = useState({

        name: "",

        description: ""

    });

    const handleChange = (e) => {

        setDepartment({

            ...department,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        await createDepartment(department);

        alert("Department Created");

    };

    return (

        <form onSubmit={handleSubmit}>

            <input

                name="name"

                placeholder="Department Name"

                onChange={handleChange}

            />

            <br /><br />

            <textarea

                name="description"

                placeholder="Description"

                onChange={handleChange}

            />

            <br /><br />

            <button>

                Save

            </button>

        </form>

    );

}

export default AddDepartment;
