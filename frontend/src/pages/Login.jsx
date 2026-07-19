import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";

function Login() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({

        email: "",
        password: ""

    });

    const handleChange = (e) => {

        setFormData({

            ...formData,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await login(formData);

            localStorage.setItem("token", response.data.token);

            localStorage.setItem("role", response.data.role);

            localStorage.setItem("name", response.data.fullName);

            alert("Login Successful");

            navigate("/dashboard");

        }

        catch (error) {

            alert("Invalid Email or Password");

            console.log(error);

        }

    };

    return (

        <div style={{ width: "350px", margin: "100px auto" }}>

            <h2>TeamPulse Login</h2>

            <form onSubmit={handleSubmit}>

                <input

                    type="email"

                    name="email"

                    placeholder="Email"

                    onChange={handleChange}

                    required

                />

                <br /><br />

                <input

                    type="password"

                    name="password"

                    placeholder="Password"

                    onChange={handleChange}

                    required

                />

                <br /><br />

                <button type="submit">

                    Login

                </button>

            </form>

        </div>

    );

}

export default Login;