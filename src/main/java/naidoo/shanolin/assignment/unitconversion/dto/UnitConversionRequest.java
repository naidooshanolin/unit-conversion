package naidoo.shanolin.assignment.unitconversion.dto;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class UnitConversionRequest {

    private String sourceUnit;
    private String destinationUnit;
    private BigDecimal quantity;

    public UnitConversionRequest(){}

    public UnitConversionRequest(String sourceUnit, String destinationUnit, BigDecimal quantity) {
        this.sourceUnit = sourceUnit;
        this.destinationUnit = destinationUnit;
        this.quantity = quantity;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getDestinationUnit() {
        return destinationUnit;
    }

    public void setDestinationUnit(String destinationUnit) {
        this.destinationUnit = destinationUnit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
