package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForDouble;

public class ParkingLotRequestDto {

  @NotNull(message = "Opening time must not be null.")
  private Time openingTime;
  @NotNull(message = "Closing time must not be null.")
  private Time closingTime;

  @PositiveOrZero(message = "Large spot fee must be non-negative.")
  private Double largeSpotFee;

  @PositiveOrZero(message = "Small spot fee must be non-negative.")
  private Double smallSpotFee;

  @PositiveOrZero(message = "Small spot monthly flat fee must be non-negative.")
  private Double smallSpotMonthlyFlatFee;

  @PositiveOrZero(message = "Large spot monthly flat fee must be non-negative.")
  private Double largeSpotMonthlyFlatFee;
	
	public void setOpeningTime(Time openingTime) {

        this.openingTime = openingTime;
	}

    public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}

    public void setLargeSpotFee(double largeSpotFee) {
		this.largeSpotFee = largeSpotFee;
	}

    public void setSmallSpotFee(double smallSpotFee) {
		this.smallSpotFee = smallSpotFee;
	}

    public void setSmallSpotMonthlyFlatFee(double smallSpotMonthlyFlatFee) {
		this.smallSpotMonthlyFlatFee = smallSpotMonthlyFlatFee;
	}

  public void setLargeSpotMonthlyFlatFee(double largeSpotMonthlyFlatFee) {
		this.largeSpotMonthlyFlatFee = largeSpotMonthlyFlatFee;
	}

    public Time getOpeningTime() { return openingTime; }

    public Time getClosingTime() { return closingTime; }

    public double getLargeSpotFee() { return largeSpotFee;}

    public double getSmallSpotFee() { return smallSpotFee; }

    public double getSmallSpotMonthlyFlatFee() { return smallSpotMonthlyFlatFee; }

    public double getLargeSpotMonthlyFlatFee() { return largeSpotMonthlyFlatFee; }

    public ParkingLot toModel() {
    ParkingLot p = new ParkingLot(openingTime, closingTime, largeSpotFee, smallSpotFee, smallSpotMonthlyFlatFee, largeSpotMonthlyFlatFee);
		return p;
	}
}
