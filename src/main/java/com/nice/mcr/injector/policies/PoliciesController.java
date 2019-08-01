package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.controller.request.DemoCreationRequest;
import com.nice.mcr.injector.controller.response.DemoResponse;
import com.nice.mcr.injector.policies.Policies;
import com.nice.mcr.injector.policies.UpdateListeners;
import com.nice.mcr.injector.service.DataGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PoliciesController  {

    @ApiOperation(value = "Elastic-Search-Policies-Test")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully called test api"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/send-data")
    public void policyTest(Policies policies) {
        policies.run();
    }
}
