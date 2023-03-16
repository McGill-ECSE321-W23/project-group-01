package ca.mcgill.ecse321.PLMS.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
public class PassController {

    @Autowired
    private PassService service;
    
}
