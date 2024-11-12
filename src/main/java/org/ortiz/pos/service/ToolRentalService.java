package org.ortiz.pos.service;

import org.ortiz.pos.service.model.RentalAgreement;
import org.ortiz.pos.service.model.ToolCode;

public interface ToolRentalService {
    RentalAgreement checkout(ToolCode toolCode, int rentalDays, int discountPercent, String checkoutDate);

}
