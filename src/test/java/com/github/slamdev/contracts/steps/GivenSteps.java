package com.github.slamdev.contracts.steps;

import com.github.slamdev.contracts.Contracts.BaseUrlStateSaver;
import com.github.slamdev.contracts.Contracts.Contract;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GivenSteps {

    @Inject
    private BaseUrlStateSaver baseUrlStateSaver;

    @Given("^baseUri for \"(.+)\" contract is \"(.+)\"$")
    public void setContractBaseUrl(Contract contract, String url) {
        log.debug("Adding base url for {} with value {}", contract, url);
        baseUrlStateSaver.put(contract, url);
    }
}
