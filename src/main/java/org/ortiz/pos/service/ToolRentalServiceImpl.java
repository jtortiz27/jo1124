package org.ortiz.pos.service;

import org.ortiz.pos.service.model.RentalAgreement;
import org.ortiz.pos.service.model.ToolCode;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ToolRentalServiceImpl implements ToolRentalService {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
    public RentalAgreement checkout(ToolCode toolCode, int rentalDays, int discountPercent, String checkoutDate) {
        validateInput(rentalDays, discountPercent);

        LocalDate checkoutLocalDate = LocalDate.parse(checkoutDate, DATE_FORMATTER);
        LocalDate dueDate = checkoutLocalDate.plusDays(rentalDays);

        int chargeDays = calculateChargeDays(toolCode, rentalDays, checkoutLocalDate);

        double preDiscountCharge = chargeDays * toolCode.getDailyCharge();
        double discountAmount = preDiscountCharge * (discountPercent / 100.0);
        double finalCharge = preDiscountCharge - discountAmount;

        return RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(toolCode.getType())
                .toolBrand(toolCode.getBrand())
                .rentalDays(rentalDays)
                .checkoutDate(checkoutLocalDate)
                .dueDate(dueDate)
                .dailyCharge(toolCode.getDailyCharge())
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(discountPercent)
                .discountAmount(discountAmount)
                .finalCharge(finalCharge)
                .build();
    }

    private void validateInput(int rentalDays, int discountPercent) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
    }

    private int calculateChargeDays(ToolCode toolCode, int rentalDays, LocalDate checkoutDate) {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate;

        for (int i = 0; i < rentalDays; i++) {
            currentDate = currentDate.plusDays(1);
            boolean isWeekend = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;
            boolean isHoliday = isHoliday(currentDate);

            if ((toolCode.isWeekdayCharge() && !isWeekend && !isHoliday) ||
                    (toolCode.isWeekendCharge() && isWeekend) ||
                    (toolCode.isHolidayCharge() && isHoliday)) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isHoliday(LocalDate date) {
        // Check for Independence Day
        LocalDate julyFourth = LocalDate.of(date.getYear(), 7, 4);
        if (julyFourth.getDayOfWeek() == DayOfWeek.SATURDAY) {
            julyFourth = julyFourth.minusDays(1);
        } else if (julyFourth.getDayOfWeek() == DayOfWeek.SUNDAY) {
            julyFourth = julyFourth.plusDays(1);
        }

        if (date.equals(julyFourth)) {
            return true;
        }

        // Check for Labor Day (first Monday of September)
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1);
        while (laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            laborDay = laborDay.plusDays(1);
        }

        return date.equals(laborDay);
    }

}
