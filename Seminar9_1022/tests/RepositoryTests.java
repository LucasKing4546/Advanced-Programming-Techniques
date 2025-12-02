import domain.Doctor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.MemoryRepository;
import repo.RepositoryException;

public class RepositoryTests {
    private MemoryRepository<Doctor> repo;

    @BeforeEach
    void setUpRepo() {
        repo = new MemoryRepository<>();
        try {
            repo.add(new Doctor(1, "Mihai", "surgery", "Cluj", 9));
            repo.add(new Doctor(2, "Andrei", "surgery", "Cluj", 9));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAdd() {
        // case 1: add does NOT throw
        try {
            repo.add(new Doctor(3, "Amma", "surgery", "Oradea", 9));
        }
        catch (RepositoryException e) {
            assert false;
        }

        // assert repo.getSize() == 3;
        Assertions.assertEquals(3, repo.getSize());

        // case 2: add throws exception
        try {
            repo.add(new Doctor(3, "Amma", "surgery", "Oradea", 9));
            assert false;
        }
        catch (RepositoryException e) {
            assert true;
        }

        Assertions.assertEquals(3, repo.getSize());
    }
}
