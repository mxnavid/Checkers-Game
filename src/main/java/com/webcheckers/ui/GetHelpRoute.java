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
@SuppressWarnings("ALL")
public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    //static constants
    public static final String TITLE_ATTR = "title";
    static final String ERROR_MESSAGE = "message";
    public static final String TITLE = "HELP";
    static final String VIEW_NAME = "help.ftl";

    //attributes
    private final TemplateEngine templateEngine;
    private ModelAndView modelAndView;


    /**
     * Constructor for the GetSignInRoute
     *
     * @param templateEngine the engine for the application
     */
    GetHelpRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine should not be null.");
        this.templateEngine = templateEngine;

    }


    /**
     * Handles the HTTP Request
     *
     * @param request request to webserver
     * @param response response from webserver
     * @return String
     */
    @SuppressWarnings("JavaDoc")
    @Override
    public String handle(Request request, Response response) {
        LOG.log(Level.WARNING, " GetHelpRoute -> invoked.");

        final Session httpSession = request.session();

        //initialize view model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);  //setting name to title attribute in ftl page
        modelAndView = new ModelAndView(vm, VIEW_NAME);
        return templateEngine.render(modelAndView);
    }


}