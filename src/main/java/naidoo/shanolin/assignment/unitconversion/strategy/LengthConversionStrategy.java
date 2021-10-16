package naidoo.shanolin.assignment.unitconversion.strategy;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;

import java.math.BigDecimal;

public class LengthConversionStrategy implements ConversionStrategy {

    private static final BigDecimal inchToMillsRatio = new BigDecimal("25.4");

    @Override
    public UnitConversionResponse convert(UnitConversionRequest conversionRequest) {

        if (conversionRequest.getSourceUnit().equals(conversionRequest.getDestinationUnit())) {
            return new UnitConversionResponse(conversionRequest.getDestinationUnit(), conversionRequest.getQuantity());
        }

        final BigDecimal quantity = conversionRequest.getQuantity();
        Unit sourceUnit = Unit.valueOf(conversionRequest.getSourceUnit());
        Unit destinationUnit = Unit.valueOf(conversionRequest.getDestinationUnit());
        final BigDecimal destinationQuantity;
        if (isSourceAndDestinationMetric(sourceUnit, destinationUnit)) {

            int scaleForSourceInMills = computeScaleForMillsConversion(sourceUnit);
            int scaleForDestinationInMills = computeScaleForMillsConversion(destinationUnit);

            BigDecimal sourceQuantityInMills = quantity.multiply(new BigDecimal(scaleForSourceInMills), mathContext);
            destinationQuantity = sourceQuantityInMills.divide(new BigDecimal(scaleForDestinationInMills), mathContext);

        } else if (isSourceAndDestinationImperial(sourceUnit, destinationUnit)) {

            int scaleForSourceInInches = computeScaleForInchConversion(sourceUnit);
            int scaleForDestinationInInches = computeScaleForInchConversion(destinationUnit);

            BigDecimal sourceQuantityInInches = quantity.multiply(new BigDecimal(scaleForSourceInInches), mathContext);
            destinationQuantity = sourceQuantityInInches.divide(new BigDecimal(scaleForDestinationInInches), mathContext);

        } else {
            destinationQuantity = computeDestinationQuantity(sourceUnit, destinationUnit, quantity);
        }

        return new UnitConversionResponse(destinationUnit.name(), destinationQuantity.setScale(SCALE, ROUNDING_MODE));
    }

    private BigDecimal computeDestinationQuantity(Unit sourceUnit, Unit destinationUnit, final BigDecimal sourceQuantity) {

        final int scaleForDestination;
        final BigDecimal destinationQuantityInLowestFamilyUnit;
        if (sourceUnit.isMetric()) {

            int scaleForSourceInMills = computeScaleForMillsConversion(sourceUnit);
            BigDecimal sourceQuantityInMills = sourceQuantity.multiply(new BigDecimal(scaleForSourceInMills), mathContext);
            destinationQuantityInLowestFamilyUnit = sourceQuantityInMills.divide(inchToMillsRatio, mathContext);
            scaleForDestination = computeScaleForInchConversion(destinationUnit);
        } else {

            int scaleForSourceInInches = computeScaleForInchConversion(sourceUnit);
            BigDecimal sourceQuantityInInches = sourceQuantity.multiply(new BigDecimal(scaleForSourceInInches), mathContext);
            destinationQuantityInLowestFamilyUnit = sourceQuantityInInches.multiply(inchToMillsRatio, mathContext);
            scaleForDestination = computeScaleForMillsConversion(destinationUnit);
        }

        // scale the destination quantity back up to destinationUnit requested
        return destinationQuantityInLowestFamilyUnit.divide(new BigDecimal(scaleForDestination), mathContext);
    }

    /**
     * For the given metricUnit, this method will return a scale value by
     * which the caller should multiply by to convert the metricUnit
     * to mm.
     *
     * The scale value could be used to convert mm to the metricUnit if
     * you divide the quantity that is in mm.
     *
     * @param metricUnit -> the unit that you want to convert to mm
     * @return scale -> the value to multiply by to get mm
     */
    private int computeScaleForMillsConversion(Unit metricUnit) {

        int scale = 1;
        if (Unit.MM == metricUnit) return scale;

        switch (metricUnit) {
            case KM:
                scale *= 1000;
            case M:
                scale *= 100;
            case CM:
                scale *= 10;
        }

        return scale;
    }

    /**
     * For the given imperialUnit, this method will return a scale value by
     * which the caller should multiply by to convert the imperialUnit
     * to inches.
     *
     * The scale value could be used to convert inches to the imperialUnit if
     * you divide the quantity that is in inches.
     *
     * @param imperialUnit -> the unit that you want to convert to inches
     * @return scale -> the value to multiply by to get inches
     */
    private int computeScaleForInchConversion(Unit imperialUnit) {

        int scale = 1;
        if (Unit.INCH == imperialUnit) return scale;

        switch (imperialUnit) {
            case MILE:
                scale *= 1760;
            case YARD:
                scale *= 3;
            case FOOT:
                scale *= 12;
        }

        return scale;
    }

    private boolean isSourceAndDestinationMetric(Unit sourceUnit, Unit destinationUnit) {
        return sourceUnit.isMetric() && destinationUnit.isMetric();
    }

    private boolean isSourceAndDestinationImperial(Unit sourceUnit, Unit destinationUnit) {
        return !sourceUnit.isMetric() && !destinationUnit.isMetric();
    }

    public enum Unit {
        INCH(false),
        FOOT(false),
        YARD(false),
        MILE(false),
        MM(true),
        CM(true),
        M(true),
        KM(true);

        private boolean isMetric;

        Unit(boolean isMetric) {
            this.isMetric = isMetric;
        }

        public boolean isMetric() {
            return isMetric;
        }
    }

}
