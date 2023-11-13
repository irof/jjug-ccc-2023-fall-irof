package dev.irof;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class SlideController {
    private static final Logger logger = LoggerFactory.getLogger(SlideController.class);

    @GetMapping("slide/{id}")
    void get(@PathVariable String id) {
        logger.info("invoke /slide/{}", id);
    }
}