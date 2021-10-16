package naidoo.shanolin.assignment.unitconversion.strategy;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;

import java.math.MathContext;
import java.math.RoundingMode;

public interface ConversionStrategy {

    MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    int SCALE = 10;
    RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    UnitConversionResponse convert(UnitConversionRequest unitConversionRequest);
}
