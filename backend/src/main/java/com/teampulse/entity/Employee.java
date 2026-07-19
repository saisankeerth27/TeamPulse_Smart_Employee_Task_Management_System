@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phone;

    private String department;

    private String designation;

    private Double salary;

    private LocalDate joiningDate;

    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}