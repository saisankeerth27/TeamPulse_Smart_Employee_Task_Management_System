@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository,
                       EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }
}
public String createTask(TaskRequest request) {

    Employee employee = employeeRepository
            .findById(request.getEmployeeId())
            .orElseThrow();

    Task task = new Task();

    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setPriority(request.getPriority());
    task.setDueDate(request.getDueDate());

    task.setStatus("PENDING");
    task.setCreatedAt(LocalDateTime.now());
    task.setEmployee(employee);

    taskRepository.save(task);

    return "Task Created Successfully";
}