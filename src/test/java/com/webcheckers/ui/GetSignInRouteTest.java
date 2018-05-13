package com.webcheckers.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Potatoes on 3/20/2018.
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

    //variables to test
    private GetSignInRoute CuT;
    private Request request;
    private Response response;
    private TemplateEngine templateEngine;

    /**
     * Initializing our mock variables
     */
    @BeforeEach
    public void setup() {
        //initialing request and response, they are needed to be passed to GetSignInRoute
        request = mock(Request.class);
        response  = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        //constructing our test object, with a mock templateEngine
        CuT = new GetSignInRoute(templateEngine);


    }

    /**
     * Tests constructing a sign in route
     */
    @Test
    public void testSignInRoute(){

        //checking the handle response returns sucesfull
        String returnString = CuT.handle(request,response);

        //grabbing the modelandview from the CuT and ensuring its non null
        ModelAndView modelAndView = mock(ModelAndView.class);
        assertNotNull(modelAndView);

        //ensures the view name is the same as the model and views view name
        assertEquals(GetSignInRoute.VIEW_NAME, modelAndView.getViewName());

        //ensuring the model is not null
        assertNotNull(modelAndView.getModel());

        //ensuring the model and view is a Map
        assertTrue(modelAndView.getModel() instanceof Map);
        //if so then cast it
        Map<String,Object> vm = (Map<String, Object>) modelAndView.getModel();
        //ensuring the vm also contains the correct title attribute
        assertEquals(GetSignInRoute.TITLE, vm.get(GetSignInRoute.TITLE_ATTR));


    }

}