package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;

/**
 * DAO class in the spring framework that acts as a link between the database and java application
 * for CRUD operations of the GuestPass class in the context of the PLMS software system
 */
public interface GuestPassRepository extends CrudRepository<GuestPass, Integer>{

    /**
     * Find the guest pass based on its id
     * @param id - id of guest pass
     * @return guestpass with id id
     */
    public GuestPass findGuestPassById(int id);

//    // Get all the guest passes on a floor with the same spot number within an interval of time
//    @Query("SELECT gp FROM GuestPass gp JOIN gp.floor f WHERE f.floorNumber = :floorNumber AND gp.spotNumber = :spotNumber AND ((gp.startTime >= :startTime AND gp.startTime < :endTime) OR (gp.endTime > :startTime AND gp.endTime <= :endTime))")
//    List<GuestPass> findByFloorAndSpotNumberAndTimePeriod(@Param("floorNumber") int floorNumber, @Param("spotNumber") String spotNumber, @Param("startTime") Time startTime, @Param("endTime") Time endTime);
}
