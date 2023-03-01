package ca.mcgill.ecse321.PLMS.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.PLMS.model.Account;
import ca.mcgill.ecse321.PLMS.repository.Account;

@SpringBootTest
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAccount(){
        //=-=-=-=-=-=- Create object -=-=-=-=-=-=//
        String email = "teta.saniya@teta.com";
        String password = "PasswordSoSuperSecured12345";
        String name = "Saniya";
    
        Account account = new Account();
        //Set all parameters
        account.setEmail(email);
        account.setPassword(password);
        account.setName(name);

        //=-=-=-=-=-=- Save account -=-=-=-=-=-=//
        account = ownerRepository.save(account);
        String accountEmail = account.getEmail();

        //=-=-=-=-=-=- Read account -=-=-=-=-=-=//
        account = accountRepository.findAccountByEmail(accountEmail);

        //=-=-=-=-=-=- Asserts -=-=-=-=-=-=//
        assertNotNull(account);
        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPassword());
        assertEquals(name, account.getName());
        
}
}
