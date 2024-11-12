package org.ortiz.pos.service;


import org.junit.jupiter.api.Test;
import org.ortiz.pos.service.model.RentalAgreement;
import org.ortiz.pos.service.model.ToolCode;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToolRentalServiceTest {
    DateTimeFormatter dateTimeFormatter = ToolRentalServiceImpl.DATE_FORMATTER;

    @Test
    void testCheckout_Ladder_ValidInput() {
        ToolRentalService rentalService = new ToolRentalServiceImpl();
        RentalAgreement agreement = rentalService.checkout(ToolCode.LADW, 3, 10, "07/02/20");

        assertEquals(ToolCode.LADW, agreement.getToolCode());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals("07/02/20", agreement.getCheckoutDate().format(dateTimeFormatter));
        assertEquals("07/05/20", agreement.getDueDate().format(dateTimeFormatter));
        assertEquals(1.99, agreement.getDailyCharge(), 0.01);
        assertEquals(2, agreement.getChargeDays()); // 1 weekday and 1 weekend day
        assertEquals(3.98, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.40, agreement.getDiscountAmount(), 0.01);
        assertEquals(3.58, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckout_Chainsaw_ValidInput() {
        ToolRentalService rentalService = new ToolRentalServiceImpl();
        RentalAgreement agreement = rentalService.checkout(ToolCode.CHNS, 5, 25, "07/02/15");

        assertEquals(ToolCode.CHNS, agreement.getToolCode());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals("07/02/15", agreement.getCheckoutDate().format(dateTimeFormatter));
        assertEquals("07/07/15", agreement.getDueDate().format(dateTimeFormatter));
        assertEquals(1.49, agreement.getDailyCharge(), 0.01);
        assertEquals(3, agreement.getChargeDays()); // 2 weekdays and 1 holiday
        assertEquals(4.47, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(1.12, agreement.getDiscountAmount(), 0.01);
        assertEquals(3.35, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckout_Jackhammer_ValidInput() {
        ToolRentalService rentalService = new ToolRentalServiceImpl();
        RentalAgreement agreement = rentalService.checkout(ToolCode.JAKR, 6, 0, "09/03/15");

        assertEquals(ToolCode.JAKR, agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals("09/03/15", agreement.getCheckoutDate().format(dateTimeFormatter));
        assertEquals("09/09/15", agreement.getDueDate().format(dateTimeFormatter));
        assertEquals(2.99, agreement.getDailyCharge(), 0.01);
        assertEquals(3, agreement.getChargeDays()); // 3 weekdays (Labor Day excluded)
        assertEquals(8.97, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        assertEquals(8.97, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckout_InvalidDiscount() {
        ToolRentalService rentalService = new ToolRentalServiceImpl();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout(ToolCode.JAKR, 5, 101, "09/03/15");
        });

        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    void testCheckout_InvalidRentalDays() {
        ToolRentalService rentalService = new ToolRentalServiceImpl();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout(ToolCode.LADW, 0, 10, "07/02/20");
        });

        assertEquals("Rental days must be 1 or greater.", exception.getMessage());
    }
}