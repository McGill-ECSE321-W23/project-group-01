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
  @PositiveOrZero(message = "Small spot monthly flat fee must be non-negative.")
  private Double smallSpotMonthlyFlatFee;
  @NotNull(message = "Monthly flat fee must not be null.")
  @PositiveOrZero(message = "Large spot monthly flat fee must be non-negative.")
  private Double largeSpotMonthlyFlatFee;
	
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

    public void setSmallSpotMonthlyFlatFee(Double smallSpotMonthlyFlatFee) {
		this.smallSpotMonthlyFlatFee = smallSpotMonthlyFlatFee;
	}

  public void setLargeSpotMonthlyFlatFee(Double largeSpotMonthlyFlatFee) {
		this.largeSpotMonthlyFlatFee = largeSpotMonthlyFlatFee;
	}
	
  public ParkingLot toModel() {
    ParkingLot p = new ParkingLot(openingTime, closingTime, largeSpotFee, smallSpotFee, smallSpotMonthlyFlatFee, largeSpotMonthlyFlatFee);
		return p;
	}
}
