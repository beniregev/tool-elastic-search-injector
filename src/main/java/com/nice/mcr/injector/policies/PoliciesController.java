package com.nice.mcr.injector.policies;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 *
 * @@@ NOT NEEDED @@@
 *
 */

public class PoliciesController  {

    @ApiOperation(value = "Elastic-Search-Policy-Test")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully called test api"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/send-data")
    public void policyTest(Policy policy) {
        policy.run();
    }
}
