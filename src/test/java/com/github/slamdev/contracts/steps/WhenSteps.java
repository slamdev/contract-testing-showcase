package com.github.slamdev.contracts.steps;

import com.github.slamdev.contracts.Contracts.BaseUrlStateSaver;
import com.github.slamdev.contracts.Contracts.ResponseStateSaver;
import com.google.inject.Inject;
import cucumber.api.java.en.When;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.ctco.cukes.rest.facade.RestRequestFacade;
import lv.ctco.cukes.rest.facade.RestResponseFacade;

import java.lang.reflect.Field;

import static com.github.slamdev.contracts.Contracts.Contract;

@Slf4j
public class WhenSteps {

    @Inject
    private ResponseStateSaver responseStateSaver;

    @Inject
    private BaseUrlStateSaver baseUrlStateSaver;

    @Inject
    private RestResponseFacade responseFacade;

    @Inject
    private RestRequestFacade requestFacade;

    @When("^the client performs (.+) request for contract on \"(.+)\"$")
    public void performContractRequest(String httpMethod, String url) {
        log.debug("Performing {} request for {}", httpMethod, url);
        baseUrlStateSaver.keySet().forEach(contract -> executeRequestAndSaveResponse(contract, httpMethod, url));
        requestFacade.initNewSpecification();
    }

    @SneakyThrows
    private void executeRequestAndSaveResponse(Contract contract, String httpMethod, String url) {
        RequestSpecification specification = requestFacade.value();
        String requestBody = requestFacade.getRequestBody();
        requestFacade.baseUri(baseUrlStateSaver.get(contract));
        responseFacade.doRequest(httpMethod, url);
        setFieldValue(RestRequestFacade.class, requestFacade, "specification", specification);
        requestFacade.setRequestBody(requestBody);
        responseStateSaver.put(contract, responseFacade.response());
    }

    @SneakyThrows
    private static void setFieldValue(Class<?> type, Object object, String name, Object value) {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, value);
    }
}
