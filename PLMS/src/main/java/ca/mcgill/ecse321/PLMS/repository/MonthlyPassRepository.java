package ca.mcgill.ecse321.PLMS.repository;
import ca.mcgill.ecse321.PLMS.model.GuestPass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.MonthlyPass;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * DAO class in the spring framework that acts as a link between the database and java application
 * for CRUD operations of the MonthlyPass class in the context of the PLMS software system
 */
public interface MonthlyPassRepository extends CrudRepository<MonthlyPass, Integer>{

    /**
     * Find a monthly pass based on id
     * @param id - id of monthly pass
     * @return MonthlyPass of id id
     */
    public MonthlyPass findMonthlyPassById(int id);

    List<MonthlyPass> findMonthlyPassesByFloorAndSpotNumberAndTimePeriod(int floorNumber, String spotNumber, Date startDate, Date endDate);

    // Get all the monthly passes on a floor with the same spot number within an interval of time
    @Query("SELECT mp FROM MonthlyPass mp JOIN mp.floor f WHERE f.floorNumber = :floorNumber AND mp.spotNumber = :spotNumber AND ((mp.startDate >= :startDate AND mp.startDate < :endDate) OR (mp.endDate > :startDate AND mp.endDate <= :endDate))")
    List<GuestPass> findByFloorAndSpotNumberAndTimePeriod(@Param("floorNumber") int floorNumber, @Param("spotNumber") String spotNumber, @Param("startTime") Time startTime, @Param("endTime") Time endTime);
}

