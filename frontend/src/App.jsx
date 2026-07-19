import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import EmployeeList from "./pages/EmployeeList";
import AddEmployee from "./pages/AddEmployee";
import DepartmentList from "./pages/DepartmentList";
import AddDepartment from "./pages/AddDepartment";
import TaskList from "./pages/TaskList";
import AddTask from "./pages/AddTask";
import Chat from "./pages/Chat";
import Analytics from "./pages/Analytics";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute>

                            <Dashboard />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/employees"
                    element={
                        <ProtectedRoute>

                            <EmployeeList />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/employees/add"
                    element={
                        <ProtectedRoute>

                            <AddEmployee />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/departments"
                    element={
                        <ProtectedRoute>

                            <DepartmentList />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/departments/add"
                    element={
                        <ProtectedRoute>

                            <AddDepartment />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/tasks"
                    element={
                        <ProtectedRoute>

                            <TaskList />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/tasks/add"
                    element={
                        <ProtectedRoute>

                            <AddTask />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/chat"
                    element={
                        <ProtectedRoute>

                            <Chat />

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/analytics"
                    element={
                        <ProtectedRoute>

                            <Analytics />

                        </ProtectedRoute>
                    }
                />

            </Routes>

        </BrowserRouter>

    );

}

export default App;