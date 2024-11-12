package org.ortiz.pos.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalAgreement {
    private ToolCode toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private Double dailyCharge;
    private int chargeDays;
    private Double preDiscountCharge;
    private int discountPercent;
    private Double discountAmount;

    //Calculated as pre-discount charge - discount amount
    private Double finalCharge;

    public void printAgreement() {
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate);
        System.out.println("Due date: " + dueDate);
        System.out.println("Daily rental charge: " + currencyFormat.format(dailyCharge));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: " + currencyFormat.format(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: " + currencyFormat.format(discountAmount));
        System.out.println("Final charge: " + currencyFormat.format(finalCharge));
    }

}
