import api from "./api";

export const getAllEmployees = () => {
    return api.get("/employees");
};

export const getEmployeeById = (id) => {
    return api.get(`/employees/${id}`);
};

export const createEmployee = (employee) => {
    return api.post("/employees", employee);
};

export const updateEmployee = (id, employee) => {
    return api.put(`/employees/${id}`, employee);
};

export const deleteEmployee = (id) => {
    return api.delete(`/employees/${id}`);
};

export const getActiveEmployees = () => {

    return api.get("/employees/active");

};

export const getEmployeesByDepartment = (id) => {

    return api.get(`/employees/department/${id}`);

};
