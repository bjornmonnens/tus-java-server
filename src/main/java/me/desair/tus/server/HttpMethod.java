package me.desair.tus.server;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Class that represents a HTTP method.
 * The X-HTTP-Method-Override request header MUST be a string which MUST be interpreted as the request’s
 * method by the Server, if the header is presented. The actual method of the request MUST be ignored.
 * The Client SHOULD use this header if its environment does not support the PATCH or DELETE methods.
 * (https://tus.io/protocols/resumable-upload.html#x-http-method-override)
 *
 */
public enum HttpMethod {

    DELETE,
    GET,
    HEAD,
    PATCH,
    POST,
    PUT,
    OPTIONS;

    public static HttpMethod forName(final String name) {
        for (HttpMethod method : HttpMethod.values()) {
            if (StringUtils.equalsIgnoreCase(method.name(), name)) {
                return method;
            }
        }

        return null;
    }

    public static HttpMethod getMethod(final HttpServletRequest request) {
        Validate.notNull(request, "The HttpServletRequest cannot be null");

        String requestMethod = request.getHeader(HttpHeader.METHOD_OVERRIDE);
        if (StringUtils.isBlank(requestMethod) || forName(requestMethod) == null) {
            requestMethod = request.getMethod();
        }

        return forName(requestMethod);
    }

}
