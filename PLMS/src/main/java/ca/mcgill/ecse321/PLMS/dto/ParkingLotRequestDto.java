package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ParkingLotRequestDto {

  @NotNull(message = "Opening time must not be null.")
  private Time openingTime;
  @NotNull(message = "Closing time must not be null.")
  private Time closingTime;
  @NotNull(message = "Large spot fee must not be null.")
  @PositiveOrZero(message = "Large spot fee must be non-negative.")
  private Double largeSpotFee;
  @NotNull(message = "Small spot fee must not be null.")
  @PositiveOrZero(message = "Small spot fee must be non-negative.")
  private Double smallSpotFee;
  @NotNull(message = "Monthly flat fee must not be null.")
  @PositiveOrZero(message = "Monthly flat fee must be non-negative.")
  private Double monthlyFlatFee;
	
	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}

    public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}

    public void setLargeSpotFee(Double largeSpotFee) {
		this.largeSpotFee = largeSpotFee;
	}

    public void setSmallSpotFee(Double smallSpotFee) {
		this.smallSpotFee = smallSpotFee;
	}

    public void setMonthlyFlatFee(Double monthlyFlatFee) {
		this.monthlyFlatFee = monthlyFlatFee;
	}
	

	
	public ParkingLot toModel() {
		ParkingLot p = new ParkingLot(openingTime, closingTime, largeSpotFee, smallSpotFee, monthlyFlatFee);
      
		return p;
	}
}
