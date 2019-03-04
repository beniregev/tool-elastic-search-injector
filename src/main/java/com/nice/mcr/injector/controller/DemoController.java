package com.nice.mcr.injector.controller;

import com.nice.mcr.injector.controller.request.DemoCreationRequest;
import com.nice.mcr.injector.controller.response.DemoResponse;
import com.nice.mcr.injector.service.DataGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/Elastic-Search-Injector")
@Api(description = "Demos API",
        value = "/demos",
        tags = "demos"
)
public class DemoController {

    @Autowired
    private DataGenerator dataGenerator;

    @ApiOperation(value = "Elastic-Search-Injector")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully called test api"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/send-data")
    public DemoResponse demoTest(@RequestBody DemoCreationRequest req) {

        DemoResponse response = null;
        try {
            dataGenerator.createData( req.getBulkSize(), req.getNumOfInteractios() );
            response = new DemoResponse();
            response.setMyRetVal( "response to Tali :)" );
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;

    }

}
