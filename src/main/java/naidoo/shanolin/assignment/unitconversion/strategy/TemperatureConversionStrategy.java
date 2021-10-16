package naidoo.shanolin.assignment.unitconversion.strategy;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;

import java.math.BigDecimal;

public class TemperatureConversionStrategy implements ConversionStrategy {

    @Override
    public UnitConversionResponse convert(UnitConversionRequest conversionRequest) {

        if (conversionRequest.getSourceUnit().equals(conversionRequest.getDestinationUnit())) {
            return new UnitConversionResponse(conversionRequest.getDestinationUnit(), conversionRequest.getQuantity());
        }

        final BigDecimal destinationQuantity;
        if (Unit.CELSIUS.name().equals(conversionRequest.getSourceUnit())) {
            destinationQuantity = computeCelsiusToFahrenheit(conversionRequest.getQuantity());
        } else {
            destinationQuantity = computeFahrenheitToCelsius(conversionRequest.getQuantity());
        }

        return new UnitConversionResponse(conversionRequest.getDestinationUnit(), destinationQuantity.setScale(SCALE, ROUNDING_MODE));
    }

    private BigDecimal computeCelsiusToFahrenheit(BigDecimal quantity) {
        return quantity.multiply(new BigDecimal("1.8000"), mathContext).add(new BigDecimal("32"));
    }

    private BigDecimal computeFahrenheitToCelsius(BigDecimal quantity) {
        return quantity.subtract(new BigDecimal("32")).divide(new BigDecimal("1.8000"), mathContext);
    }

    public enum Unit {
        CELSIUS,FAHRENHEIT;
    }
}
