package com.mertugrul.LeisurePass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VerificationRespond {

    private @NotEmpty @JsonProperty("PassId") String passId;
    private @NotNull @JsonProperty("VendorId") Long vendorId;
    private @NotNull @JsonProperty("PassValidation") Boolean isValid;

}
