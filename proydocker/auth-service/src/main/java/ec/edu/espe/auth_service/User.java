@Entity
@Table(name = "users")
@Data // de Lombok
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    
}