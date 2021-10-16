package naidoo.shanolin.assignment.unitconversion.dto;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class UnitConversionResponse {

    private String unit;
    private BigDecimal quantity;

    public UnitConversionResponse(){}

    public UnitConversionResponse(String unit, BigDecimal quantity) {
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
