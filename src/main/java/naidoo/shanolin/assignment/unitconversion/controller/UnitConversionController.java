package naidoo.shanolin.assignment.unitconversion.controller;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;
import naidoo.shanolin.assignment.unitconversion.service.UnitConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UnitConversionController {

    private UnitConversionService unitConversionService;

    @Autowired
    public UnitConversionController(UnitConversionService unitConversionService) {
        this.unitConversionService = unitConversionService;
    }

    @GetMapping("/convert-unit")
    public UnitConversionResponse getConvertedUnit(
            @RequestParam("sourceUnit") String sourceUnit,
            @RequestParam("destinationUnit") String destinationUnit,
            @RequestParam("quantity") BigDecimal quantity) {

        UnitConversionRequest conversionRequest = new UnitConversionRequest(sourceUnit.toUpperCase(), destinationUnit.toUpperCase(), quantity);
        return unitConversionService.convertUnit(conversionRequest);
    }
}
