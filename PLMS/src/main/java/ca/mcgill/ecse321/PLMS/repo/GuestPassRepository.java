package ca.mcgill.ecse321.PLMS.repo;

import ca.mcgill.ecse321.PLMS.model.GuestPass;
import org.springframework.data.repository.CrudRepository;

public interface GuestPassRepository extends CrudRepository<GuestPass, Integer> {
    public GuestPass findGuestPassById(int id);
}
