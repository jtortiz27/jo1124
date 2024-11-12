package org.ortiz.pos.service.model;

public enum ToolCode {
    CHNS("Chainsaw", "Stihl", 1.49, true, false, true),
    LADW("Ladder", "Werner", 1.99, true, true, false),
    JAKD("Jackhammer", "DeWalt", 2.99, true, false, false),
    JAKR("Jackhammer", "Ridgid", 2.99, true, false, false);

    private final String type;
    private final String brand;
    private final double dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    ToolCode(String type, String brand, double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.type = type;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }
}

