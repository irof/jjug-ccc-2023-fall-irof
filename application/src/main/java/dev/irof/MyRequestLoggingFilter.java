package dev.irof;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Component
public class MyRequestLoggingFilter extends AbstractRequestLoggingFilter {
    private static final Logger logger = LoggerFactory.getLogger(MyRequestLoggingFilter.class);

    private static final String TIMESTAMP_KEY = MyRequestLoggingFilter.class.getCanonicalName();

    @Override
    protected void beforeRequest(HttpServletRequest request, @NonNull String message) {
        request.setAttribute(TIMESTAMP_KEY, System.currentTimeMillis());
        //logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, @NonNull String message) {
        if (request.getAttribute(TIMESTAMP_KEY) instanceof Long startTime) {
            logger.info("{}: taken time={}ms", message, System.currentTimeMillis() - startTime);
        } else {
            logger.info(message);
        }
    }
}
