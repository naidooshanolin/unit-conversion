package naidoo.shanolin.assignment.unitconversion.service;

import lombok.extern.slf4j.Slf4j;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionResponse;
import naidoo.shanolin.assignment.unitconversion.strategy.ConversionStrategy;
import naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy;
import naidoo.shanolin.assignment.unitconversion.strategy.TemperatureConversionStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UnitConversionService {

    private Map<Set<String>, ConversionStrategy> conversionStrategies;

    public UnitConversionService() {

        conversionStrategies = new HashMap<>();

        Set<String> lengthUnits = Arrays.stream(LengthConversionStrategy.Unit.values()).map(unit -> unit.name()).collect(Collectors.toSet());
        conversionStrategies.put(lengthUnits, new LengthConversionStrategy());

        Set<String> tempUnits = Arrays.stream(TemperatureConversionStrategy.Unit.values()).map(unit -> unit.name()).collect(Collectors.toSet());
        conversionStrategies.put(tempUnits, new TemperatureConversionStrategy());
    }

    public UnitConversionResponse convertUnit(UnitConversionRequest conversionRequest) {

        ConversionStrategy conversionStrategy = getStrategy(conversionRequest);
        return conversionStrategy.convert(conversionRequest);
    }

    private ConversionStrategy getStrategy(UnitConversionRequest conversionRequest) {

        for (Map.Entry<Set<String>, ConversionStrategy> entry : conversionStrategies.entrySet()) {

            Set<String> supportedUnits = entry.getKey();
            if (supportedUnits.contains(conversionRequest.getSourceUnit()) &&
                    supportedUnits.contains(conversionRequest.getDestinationUnit())) {

                return entry.getValue();
            }
        }

        String message = String.format("Conversion between sourceUnit=%s and destinationUnit=%s is not supported.",
                conversionRequest.getSourceUnit(), conversionRequest.getDestinationUnit());

        log.warn(message);
        throw new ServiceException(message);
    }
}
