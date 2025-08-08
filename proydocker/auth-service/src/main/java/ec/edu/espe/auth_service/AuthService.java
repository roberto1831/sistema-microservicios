@Service
@RequiredArgsConstructor // de Lombok
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya registrado");
        }
        // Hashear la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Publicar evento de registro de usuario a RabbitMQ
        rabbitTemplate.convertAndSend("publication.events", "user.registered", savedUser);

        return savedUser;
    }
}