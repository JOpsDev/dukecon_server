package org.dukecon.server.core

import groovy.transform.TypeChecked
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ascheman on 26.05.17.
 */
class DukeConErrorControllerConstants {
    static final String PATH = "/error"
}

@RestController
@TypeChecked
public class DukeConErrorController implements ErrorController {

    private static final Logger logger = Logger.getLogger(DukeConErrorController.class)

    @Autowired
    private ErrorAttributes errorAttributes

    @Value("\${application.debug:false}")
    private boolean debug

    @Override
    public String getErrorPath() {
        return DukeConErrorControllerConstants.PATH
    }

    @RequestMapping(DukeConErrorControllerConstants.PATH)
    public Map<String, Object> error(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = [:]
        map.put("status", response.getStatus())
        map.put("reason", getErrorAttributes(request, debug))
        logger.warn(map)
        return map
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request)
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace)
    }
}