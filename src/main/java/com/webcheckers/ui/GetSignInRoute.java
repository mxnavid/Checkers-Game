package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SignIn Route for the Checker Game
 */
public class GetSignInRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    //static constants
    public static final String TITLE_ATTR = "title";
    static final String ERROR_MESSAGE = "message";
    public static final String TITLE = "Sign In";
    final static String USERNAME_FIELD = "name";
    static final String VIEW_NAME = "signin.ftl";

    //attributes
    private final TemplateEngine templateEngine;


    /**
     * Constructor for the GetSignInRoute
     * @param templateEngine the engine for application
     */
    @SuppressWarnings("JavaDoc")
    GetSignInRoute(final TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.templateEngine = templateEngine;

    }


    /**
     * Handles the HTTP Request
     * @param request request to webserver
     * @param response response from webserver
     * @return String
     */
    @SuppressWarnings("JavaDoc")
    @Override
    public String handle(Request request, Response response){
        LOG.log(Level.WARNING," GetGameRoute -> invoked.");


        //initialize view model
        final Map<String,Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);  //setting name to title attribute in ftl page
        ModelAndView modelAndView = new ModelAndView(vm, VIEW_NAME);
        return templateEngine.render(modelAndView);
    }

}