package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repo;


    @Test
    public void persistAndLoadEmployee1(){
        Employee samer = new Employee("bllo", "bla","samer", "prez", 21);
        repo.save(samer);
        assertEquals(repo.findEmployeeByEmail("bllo"), repo.findEmployeeById("bllo"));
        assertEquals(repo.findEmployeeById("bllo").getEmail(), "bllo");
        assertEquals(repo.findEmployeeByEmail("bllo").getEmail(), "bllo");

    }
}
