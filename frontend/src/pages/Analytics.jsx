import { useEffect, useState } from "react";
import { getAnalytics } from "../services/analyticsService";
import { jsPDF } from "jspdf";
import * as XLSX from "xlsx";
import { Pie, Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement
} from "chart.js";

ChartJS.register(
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement
);

function Analytics() {

    const [analytics, setAnalytics] = useState({});

    useEffect(() => {

        loadAnalytics();

    }, []);

    const loadAnalytics = async () => {

        const response = await getAnalytics();

        setAnalytics(response.data);

    };

    const exportPDF = () => {

        const doc = new jsPDF();

        doc.setFontSize(18);
        doc.text("TeamPulse Analytics Report", 20, 20);

        doc.setFontSize(12);
        doc.text(`Total Employees: ${analytics.totalEmployees}`, 20, 40);
        doc.text(`Total Departments: ${analytics.totalDepartments}`, 20, 50);
        doc.text(`Total Tasks: ${analytics.totalTasks}`, 20, 60);
        doc.text(`Completion Rate: ${analytics.completionRate?.toFixed(1)}%`, 20, 70);
        doc.text(`Avg Completion Hours: ${analytics.averageCompletionHours?.toFixed(1)}`, 20, 80);

        if (analytics.topEmployees) {
            doc.text("Top Performers:", 20, 100);
            analytics.topEmployees.forEach((emp, i) => {
                doc.text(
                    `${i + 1}. ${emp.employeeName} - ${emp.completedTasks} tasks`,
                    20,
                    110 + i * 10
                );
            });
        }

        doc.save("analytics-report.pdf");

    };

    const exportExcel = () => {

        const data = [
            {
                Metric: "Total Employees",
                Value: analytics.totalEmployees
            },
            {
                Metric: "Total Departments",
                Value: analytics.totalDepartments
            },
            {
                Metric: "Total Tasks",
                Value: analytics.totalTasks
            },
            {
                Metric: "Completion Rate (%)",
                Value: analytics.completionRate?.toFixed(1)
            },
            {
                Metric: "Avg Completion Hours",
                Value: analytics.averageCompletionHours?.toFixed(1)
            }
        ];

        const ws = XLSX.utils.json_to_sheet(data);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Analytics");
        XLSX.writeFile(wb, "analytics-report.xlsx");

    };

    const pieData = {
        labels: ["Completed", "In Progress", "Todo", "Blocked"],
        datasets: [
            {
                data: [
                    analytics.completedTasks || 0,
                    analytics.inProgressTasks || 0,
                    analytics.todoTasks || 0,
                    analytics.blockedTasks || 0
                ],
                backgroundColor: [
                    "#4caf50",
                    "#2196f3",
                    "#ff9800",
                    "#f44336"
                ]
            }
        ]
    };

    const barData = {
        labels: analytics.topEmployees?.map(e => e.employeeName) || [],
        datasets: [
            {
                label: "Completed Tasks",
                data: analytics.topEmployees?.map(e => e.completedTasks) || [],
                backgroundColor: "#4caf50"
            }
        ]
    };

    return (

        <div>

            <h2>Analytics Dashboard</h2>

            <div style={{ display: "flex", gap: "20px", flexWrap: "wrap", marginBottom: "20px" }}>

                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Employees <h3>{analytics.totalEmployees}</h3>
                </div>
                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Departments <h3>{analytics.totalDepartments}</h3>
                </div>
                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Total Tasks <h3>{analytics.totalTasks}</h3>
                </div>
                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Completion Rate <h3>{analytics.completionRate?.toFixed(1)}%</h3>
                </div>
                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "8px", minWidth: "150px" }}>
                    Avg Hours <h3>{analytics.averageCompletionHours?.toFixed(1)}</h3>
                </div>

            </div>

            <div style={{ display: "flex", gap: "40px", flexWrap: "wrap", marginBottom: "20px" }}>

                <div style={{ width: "300px" }}>
                    <h3>Task Status</h3>
                    <Pie data={pieData} />
                </div>

                <div style={{ width: "400px" }}>
                    <h3>Top Performers</h3>
                    <Bar data={barData} />
                </div>

            </div>

            {analytics.topEmployees && analytics.topEmployees.length > 0 && (

                <div style={{ marginBottom: "20px" }}>

                    <h3>Top Performers</h3>
                    <ol>
                        {analytics.topEmployees.map((emp, i) => (
                            <li key={emp.employeeId}>
                                {emp.employeeName} - {emp.completedTasks} tasks completed
                            </li>
                        ))}
                    </ol>

                </div>

            )}

            <div style={{ display: "flex", gap: "10px" }}>

                <button onClick={exportPDF}>Export PDF</button>
                <button onClick={exportExcel}>Export Excel</button>

            </div>

        </div>

    );

}

export default Analytics;
