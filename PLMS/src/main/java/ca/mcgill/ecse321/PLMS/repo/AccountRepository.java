package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    public Account findAccountByEmail(String email);
}
