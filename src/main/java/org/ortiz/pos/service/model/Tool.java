package org.ortiz.pos.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool {
        ToolCode code;
        String type;
        String brand;
        double dailyCharge;
        boolean weekdayCharge;
        boolean weekendCharge;
        boolean holidayCharge;
}
