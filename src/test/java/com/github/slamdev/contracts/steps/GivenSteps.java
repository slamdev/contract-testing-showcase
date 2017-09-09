package com.github.slamdev.contracts.steps;

import com.github.slamdev.contracts.Contracts.BaseUrlStateSaver;
import com.github.slamdev.contracts.Contracts.Contract;
import com.github.slamdev.contracts.Contracts.HeadersExcluder;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import lombok.extern.slf4j.Slf4j;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.List;

@Slf4j
public class GivenSteps {

    @Inject
    private BaseUrlStateSaver baseUrlStateSaver;

    @Inject
    private GlobalWorldFacade world;

    @Given("^baseUri for \"(.+)\" contract is \"(.+)\"$")
    public void setContractBaseUrl(Contract contract, String url) {
        log.debug("Adding base url for {} with value {}", contract, url);
        baseUrlStateSaver.put(contract, url);
    }

    @Given("^exclude headers from verification: (.*)$")
    public void excludeHeadersFromVerification(List<String> headerNames) {
        log.debug("Excluding headers from verification: {}", headerNames);
        HeadersExcluder.exclude(world, headerNames);
    }
}
