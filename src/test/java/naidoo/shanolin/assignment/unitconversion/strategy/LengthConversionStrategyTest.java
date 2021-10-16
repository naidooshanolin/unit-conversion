package naidoo.shanolin.assignment.unitconversion.strategy;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.CM;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.FOOT;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.INCH;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.KM;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.M;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.MILE;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.MM;
import static naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy.Unit.YARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LengthConversionStrategyTest {

    private LengthConversionStrategy lengthConversionStrategy;
    private int precision;

    @BeforeEach
    public void init() {
        lengthConversionStrategy = new LengthConversionStrategy();
        precision = ConversionStrategy.mathContext.getPrecision();
    }

    @Test
    public void testConversionWhenSourceAndDestinationUnitIsSame() {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                LengthConversionStrategy.Unit.MM.name(),
                new BigDecimal("1")
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.MM.name(),
                LengthConversionStrategy.Unit.MM.name(),
                new BigDecimal("1")
        );

        UnitConversionResponse conversionResponse = lengthConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }

    @Test
    public void testConvertWhenSourceAndDestinationUnitIsMetric() {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                LengthConversionStrategy.Unit.CM.name(),
                new BigDecimal("100").setScale(precision)
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.M.name(),
                LengthConversionStrategy.Unit.CM.name(),
                new BigDecimal("1")
        );

        UnitConversionResponse conversionResponse = lengthConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }

    @Test
    public void testConvertWhenSourceAndDestinationUnitIsImperial() {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                LengthConversionStrategy.Unit.MILE.name(),
                new BigDecimal("0.0001893939").setScale(precision)
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.FOOT.name(),
                LengthConversionStrategy.Unit.MILE.name(),
                new BigDecimal("1")
        );

        UnitConversionResponse conversionResponse = lengthConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }

    @ParameterizedTest
    @MethodSource("provideParametersForConvertTest")
    public void testConversion(
            String sourceUnit, String destinationUnit, String expectedQuantity, String sourceQuantity) {

        UnitConversionResponse expectedConversion = new UnitConversionResponse(
                destinationUnit,
                new BigDecimal(expectedQuantity).setScale(precision)
        );

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                sourceUnit,
                destinationUnit,
                new BigDecimal(sourceQuantity)
        );

        UnitConversionResponse conversionResponse = lengthConversionStrategy.convert(conversionRequest);
        assertEquals(expectedConversion, conversionResponse);
    }

    private static Stream<Arguments> provideParametersForConvertTest() {

        return Stream.of(
                /*
                    String sourceUnit, String destinationUnit, String expectedQuantity, String sourceQuantity
                */
                Arguments.of(MM.name(), INCH.name(), "1", "25.4"),
                Arguments.of(MM.name(), FOOT.name(), "1", "304.8"),
                Arguments.of(MM.name(), YARD.name(), "1", "914.4"),
                Arguments.of(MM.name(), MILE.name(), "0.5", "804672"),

                Arguments.of(CM.name(), INCH.name(), "1", "2.54"),
                Arguments.of(CM.name(), FOOT.name(), "1", "30.48"),
                Arguments.of(CM.name(), YARD.name(), "1", "91.44"),
                Arguments.of(CM.name(), MILE.name(), "0.5", "80467.2"),

                Arguments.of(M.name(), INCH.name(), "1", "0.0254"),
                Arguments.of(M.name(), FOOT.name(), "1", "0.3048"),
                Arguments.of(M.name(), YARD.name(), "1", "0.9144"),
                Arguments.of(M.name(), MILE.name(), "0.5", "804.672"),

                Arguments.of(KM.name(), INCH.name(), "10000", "0.254"),
                Arguments.of(KM.name(), FOOT.name(), "10000", "3.048"),
                Arguments.of(KM.name(), YARD.name(), "100000", "91.44"),

                Arguments.of(KM.name(), M.name(), "1000", "1"),
                Arguments.of(KM.name(), CM.name(), "100000", "1"),
                Arguments.of(KM.name(), MM.name(), "1000000", "1"),

                Arguments.of(M.name(), CM.name(), "100", "1"),
                Arguments.of(M.name(), MM.name(), "1000", "1"),

                Arguments.of(CM.name(), MM.name(), "10", "1"),

                Arguments.of(MILE.name(), YARD.name(), "1760", "1"),
                Arguments.of(MILE.name(), FOOT.name(), "5280", "1"),
                Arguments.of(MILE.name(), INCH.name(), "63360", "1"),

                Arguments.of(YARD.name(), FOOT.name(), "3", "1"),
                Arguments.of(YARD.name(), INCH.name(), "36", "1"),

                Arguments.of(FOOT.name(), INCH.name(), "12", "1")
        );
    }
}
