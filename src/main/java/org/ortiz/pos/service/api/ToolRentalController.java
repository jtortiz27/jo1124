package org.ortiz.pos.service.api;

import org.ortiz.pos.service.ToolRentalService;
import org.ortiz.pos.service.model.RentalAgreement;
import org.ortiz.pos.service.model.ToolCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toolRentals")
public class ToolRentalController {

    private ToolRentalService toolRentalService;

    public ToolRentalController(@Autowired ToolRentalService toolRentalService) {
        this.toolRentalService = toolRentalService;
    }

    @PostMapping
    public ResponseEntity<?> checkout (ToolCode toolCode, int rentalDays, int discountPercent, String checkoutDate) {
        return ResponseEntity.ok(toolRentalService.checkout(toolCode,rentalDays,discountPercent,checkoutDate));
    }
}
