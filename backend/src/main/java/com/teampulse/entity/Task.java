@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String priority;

    private String status;

    private LocalDateTime createdAt;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

}