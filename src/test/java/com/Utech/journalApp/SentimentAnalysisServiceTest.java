package com.Utech.journalApp;

import com.Utech.journalApp.Entity.Sentiment;
import com.Utech.journalApp.services.SentimentAnalysisService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentimentAnalysisServiceTest {
    private final SentimentAnalysisService sentimentAnalysisService = new SentimentAnalysisService();

    @Test
    void classifiesPositiveText() {
        assertEquals(Sentiment.POSITIVE, sentimentAnalysisService.classify("I feel grateful, calm, and motivated today"));
    }

    @Test
    void classifiesNegativeText() {
        assertEquals(Sentiment.NEGATIVE, sentimentAnalysisService.classify("I am anxious, stressed, and overwhelmed"));
    }

    @Test
    void classifiesNeutralText() {
        assertEquals(Sentiment.NEUTRAL, sentimentAnalysisService.classify("I wrote in my journal after lunch"));
    }
}
