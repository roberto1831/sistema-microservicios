public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}