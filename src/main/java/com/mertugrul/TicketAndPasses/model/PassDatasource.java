package com.mertugrul.TicketAndPasses.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassDatasource {

    private @NotEmpty @JsonProperty("PassCity") String city;
    private @NotEmpty @JsonProperty("CustomerId") String customerId;
    private @NotEmpty @JsonProperty("VendorId") String vendorId;
    private @NotNull  @JsonProperty("PassLength") @Min(1) @Max(336) int passLength;

}
