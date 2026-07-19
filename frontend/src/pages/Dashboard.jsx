import { useNavigate } from "react-router-dom";

function Dashboard() {

    const navigate = useNavigate();

    const logout = () => {

        localStorage.clear();

        navigate("/");

    };

    return (

        <div>

            <h1>

                Welcome {localStorage.getItem("name")}

            </h1>

            <h3>

                Role : {localStorage.getItem("role")}

            </h3>

            <button onClick={logout}>

                Logout

            </button>

        </div>

    );

}

export default Dashboard;