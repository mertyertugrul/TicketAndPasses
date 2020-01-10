package com.mertugrul.LeisurePass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePassRequest {
    private @NotEmpty @JsonProperty("PassId") String passId;
    private @NotNull @JsonProperty("UpdateLength") @Min(1) @Max(365) int updateLength;
    private @NotNull @JsonProperty("UpdateActivationDate") Boolean isUpdateActivationDate;
}
