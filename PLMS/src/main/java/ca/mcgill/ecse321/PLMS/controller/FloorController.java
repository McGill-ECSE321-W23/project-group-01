package ca.mcgill.ecse321.PLMS.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.FloorDto;
import ca.mcgill.ecse321.PLMS.model.Floor;
import jakarta.validation.Valid;

@RestController
public class FloorController {
  @Autowired
  private FloorService floorService;

  /**
   * Gets all floors.
   * 
   * @return All floors.
   */
  @GetMapping("/floor")
  public Iterable<FloorDto> getAllFloors(){
    return StreamSupport.stream(floorService.getAllFloors().spliterator(), false).map(f -> new FloorDto(f)).collect(Collectors.toList());
  }

  /**
   * Gets a floor by the floor number
   * 
   * @return floor with floorNumber
   */
  @GetMapping("/floor/{floorNumber}")
  public ResponseEntity<FloorDto> getFloorByFloorNumber(@PathVariable int floorNumber){
    Floor floor = floorService.getFloorById(floorNumber);
    FloorDto responseBody = new FloorDto(floor);
    return new ResponseEntity<FloorDto>(responseBody, HttpStatus.OK);
  }

  /**
   * Creates a floor.
   * 
   * @return floorDto of the created floor
   */
  @PostMapping("/floor")
  public ResponseEntity<FloorDto> createFloor(@Valid @RequestBody FloorDto floorDto){
    Floor floor = floorDto.toModel();
    floor = floorService.createFloor(floor);
    FloorDto responseBody = new FloorDto(floor);
    return new ResponseEntity<FloorDto>(responseBody, HttpStatus.CREATED);
  }

  /**
   * Allows updates for all floor variables
   * 
   * @return floor with updated values
   */
  @PutMapping("/floor/{floorNumber}")
  public ResponseEntity<FloorDto> updateFloorInfo(@PathVariable int floorNumber, @RequestBody FloorDto floorDto){
    Floor floor = floorDto.toModel();
    floor = floorService.updateFloor(floor);
    FloorDto responseBody = new FloorDto(floor);
    return new ResponseEntity<FloorDto>(responseBody, HttpStatus.CREATED);
  }

  /**
   * Deletes a floor
   */
  @DeleteMapping("/floor/{id}")
  public void deleteFloor(@PathVariable int floorNumber){
    floorService.deleteFloor(floorNumber);
  }

}
