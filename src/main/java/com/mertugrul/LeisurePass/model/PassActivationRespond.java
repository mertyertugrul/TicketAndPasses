package com.mertugrul.LeisurePass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PassActivationRespond {

    private @NotEmpty @JsonProperty("PassId") String PassId;
    private @NotNull @JsonProperty("PassActivation") Boolean isActive;
}
