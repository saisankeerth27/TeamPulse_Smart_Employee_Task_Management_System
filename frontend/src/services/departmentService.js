import api from "./api";

export const getDepartments = () => {

    return api.get("/departments");

};

export const createDepartment = (department) => {

    return api.post("/departments", department);

};

export const updateDepartment = (id, department) => {

    return api.put(`/departments/${id}`, department);

};

export const deleteDepartment = (id) => {

    return api.delete(`/departments/${id}`);

};
