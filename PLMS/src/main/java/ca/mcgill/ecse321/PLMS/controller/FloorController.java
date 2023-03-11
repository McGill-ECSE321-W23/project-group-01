package ca.mcgill.ecse321.PLMS.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.PLMS.dto.FloorDto;
import ca.mcgill.ecse321.PLMS.model.Floor;

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

  @GetMapping("/floor/{id}")
  public ResponseEntity<FloorDto> getFloorById(@PathVariable int id){
    Floor floor = floorService.getFloorById(id);
    FloorDto responseBody = new FloorDto(floor);
    return new ResponseEntity<FloorDto>(responseBody, HttpStatus.OK);
  }

}
