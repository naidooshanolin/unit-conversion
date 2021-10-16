package naidoo.shanolin.assignment.unitconversion.controller;

import naidoo.shanolin.assignment.unitconversion.dto.UnitConversionRequest;
import naidoo.shanolin.assignment.unitconversion.service.UnitConversionService;
import naidoo.shanolin.assignment.unitconversion.strategy.LengthConversionStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = {UnitConversionController.class})
public class UnitConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnitConversionService unitConversionService;

    @Autowired
    private UnitConversionController unitConversionController;

    @Test
    public void givenMalformedRequest_whenRequestingUnitConversion_thenExpectBadRequest() throws Exception {

        String path = String.format("/convert-unit?sourceUnit=%s&destinationUnit=%s&quantity=%s",
                "sourceUnit", "destinationUnit", "malformed");

        mockMvc.perform(MockMvcRequestBuilders.get(path)).andExpect(status().isBadRequest());
        verify(unitConversionService, never()).convertUnit(any(UnitConversionRequest.class));
    }

    @Test
    public void testGetConvertedUnit() throws Exception {

        UnitConversionRequest conversionRequest = new UnitConversionRequest(
                LengthConversionStrategy.Unit.MM.name(),
                LengthConversionStrategy.Unit.M.name(),
                BigDecimal.TEN.setScale(2)
        );

        String path = String.format("/convert-unit?sourceUnit=%s&destinationUnit=%s&quantity=%.2f",
                conversionRequest.getSourceUnit(), conversionRequest.getDestinationUnit(), conversionRequest.getQuantity());

        mockMvc.perform(MockMvcRequestBuilders.get(path)).andExpect(status().isOk());
        verify(unitConversionService).convertUnit(conversionRequest);
    }
}
