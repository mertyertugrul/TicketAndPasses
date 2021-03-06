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
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassRequest {
    private @NotEmpty @JsonProperty("PassId") String passId;
    private @NotNull @JsonProperty("UpdateLength") @Min(1) @Max(336) int updateLength;
    private @NotNull @JsonProperty("UpdateActivationDate") Boolean isUpdateActivationDate;
}
