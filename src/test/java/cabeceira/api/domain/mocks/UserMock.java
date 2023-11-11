package cabeceira.api.domain.mocks;
import cabeceira.api.domain.user.User;

public class UserMock {
    public static User create() {
        return new User(
                "user123",
                "John",
                "Doe",
                "password123",
                "john.doe@example.com"
        );
    }
}
