package com.github.slamdev.contracts.steps;

import com.github.slamdev.contracts.Contracts.ResponseStateSaver;
import com.google.inject.Inject;
import cucumber.api.java.en.Then;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.slamdev.contracts.Contracts.Contract.LEGACY;
import static com.github.slamdev.contracts.Contracts.Contract.NEW;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
public class ThenSteps {

    @Inject
    private ResponseStateSaver responseStateSaver;

    @Inject
    private GlobalWorldFacade world;

    @Then("^verify contract requests are same")
    public void assertRequestsEqual() {
        log.debug("Verifying requests");
        assertBodiesEqual();
        assertHeadersEqual();
        assertStatusCodesEqual();
        assertContentTypesEqual();
    }

    private void assertBodiesEqual() {
        assertThat(legacyResponse().body().asString(), equalTo(newResponse().body().asString()));
    }

    private void assertHeadersEqual() {
        assertThat(headers(legacyResponse()), containsInAnyOrder(headers(newResponse()).toArray()));
    }

    private void assertStatusCodesEqual() {
        assertThat(legacyResponse().statusCode(), equalTo(newResponse().statusCode()));
    }

    private void assertContentTypesEqual() {
        assertThat(legacyResponse().contentType(), equalTo(newResponse().contentType()));
    }

    private List<Header> headers(Response response) {
        List<String> excludeHeaderNames = Arrays.stream(world.get("excludeHeadersFromVerification")
                .or("").split(",")).map(String::trim).collect(toList());
        return response.headers().asList().stream()
                .filter(h -> !excludeHeaderNames.contains(h.getName())).collect(toList());
    }

    private Response newResponse() {
        return Optional.ofNullable(responseStateSaver.get(NEW)).orElseThrow(IllegalStateException::new);
    }

    private Response legacyResponse() {
        return Optional.ofNullable(responseStateSaver.get(LEGACY)).orElseThrow(IllegalStateException::new);
    }
}
