package ca.mcgill.ecse321.PLMS.dto;

import java.sql.Time;

import ca.mcgill.ecse321.PLMS.model.ParkingLot;

public class ParkingLotResponseDto {
    private int id;
    private Time openingTime;
    private Time closingTime;
    private Double largeSpotFee;
    private Double smallSpotFee;
    private Double smallSpotMonthlyFlatFee;
		private Double largeSpotMonthlyFlatFee;
	
	public ParkingLotResponseDto(ParkingLot p) {
		this.id = p.getId();
		this.openingTime = p.getOpeningTime();
		this.closingTime = p.getClosingTime();
        this.largeSpotFee = p.getLargeSpotFee();
        this.smallSpotFee = p.getSmallSpotFee();
        this.smallSpotMonthlyFlatFee = p.getSmallSpotMonthlyFlatFee();
				this.largeSpotMonthlyFlatFee = p.getLargeSpotMonthlyFlatFee();
	}

	public ParkingLotResponseDto() {}
	
	public int getId() {
		return id;
	}

    public Time getOpeningTime() {
		return openingTime;
	}

    public Time getClosingTime() {
		return closingTime;
	}

    public Double getLargeSpotFee() {
		return largeSpotFee;
	}

    public Double getSmallSpotFee() {
		return smallSpotFee;
	}

	public Double getSmallSpotMonthlyFlatFee() {
		return smallSpotMonthlyFlatFee;
	}

	public Double getLargeSpotMonthlyFlatFee() {
		return largeSpotMonthlyFlatFee;
	}
}
