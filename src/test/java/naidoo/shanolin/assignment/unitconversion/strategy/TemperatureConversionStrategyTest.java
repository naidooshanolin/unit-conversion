package naidoo.shanolin.assignment.unitconversion.strategy;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static naidoo.shanolin.assignment.unitconversion.strategy.TemperatureConversionStrategy.Unit.CELSIUS;
import static naidoo.shanolin.assignment.unitconversion.strategy.TemperatureConversionStrategy.Unit.FAHRENHEIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureConversionStrategyTest {

    private TemperatureConversionStrategy temperatureConversionStrategy;
    private int precision;

    @BeforeEach
    public void init()  {
        temperatureConversionStrategy = new TemperatureConversionStrategy();
        precision = ConversionStrategy.mathContext.getPrecision();
    }

    @Test
    public void testConvertFromCelsiusToFahrenheit() {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                FAHRENHEIT.name(),
                new BigDecimal("32").setScale(precision)
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                CELSIUS.name(),
                FAHRENHEIT.name(),
                new BigDecimal("0")
        );

        UnitConversionResponse conversionResponse = temperatureConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }

    @Test
    public void testConvertFromFahrenheitToCelsius() {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                CELSIUS.name(),
                new BigDecimal("0").setScale(precision)
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                FAHRENHEIT.name(),
                CELSIUS.name(),
                new BigDecimal("32")
        );

        UnitConversionResponse conversionResponse = temperatureConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }
}
