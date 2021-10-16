package naidoo.shanolin.assignment.unitconversion.service;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;
import naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy;
import naidoo.shanolin.assignment.unitconversion.strategy.TemperatureConversionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitConversionServiceTest {

    private UnitConversionService unitConversionService;

    @BeforeEach
    private void init() {
        unitConversionService = new UnitConversionService();
    }

    @Test
    public void testThatServiceExceptionThrownWhenUnsupportedConversionRequested() {

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.KM.name(),
                TemperatureConversionStrategy.Unit.CELSIUS.name(),
                BigDecimal.TEN
        );

        assertThrows(ServiceException.class, () -> unitConversionService.convertUnit(conversionRequest));
    }

    @Test
    public void testConvertUnit() {

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.KM.name(),
                LengthConversionStrategy.Unit.YARD.name(),
                BigDecimal.TEN
        );

        UnitConversionResponse conversionResponse = unitConversionService.convertUnit(conversionRequest);
        assertNotNull(conversionResponse);
    }
}
