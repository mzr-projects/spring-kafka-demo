package com.mt.kafka.real;

import com.launchdarkly.eventsource.StreamException;
import com.mt.kafka.real.producers.WikiMediaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wiki-media")
public class WikiMediaController {

    private final WikiMediaProducer<String> wikiMediaProducer;

    public WikiMediaController(WikiMediaProducer<String> wikiMediaProducer) {
        this.wikiMediaProducer = wikiMediaProducer;
    }

    @GetMapping("/start")
    public String startStream() throws StreamException {
        wikiMediaProducer.startStream();
        return "Stream started.";
    }
}
