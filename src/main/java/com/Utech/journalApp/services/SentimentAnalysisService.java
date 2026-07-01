package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.Sentiment;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class SentimentAnalysisService {
    private static final Pattern WORD_SPLIT = Pattern.compile("[^a-zA-Z']+");
    private static final Set<String> POSITIVE_WORDS = Set.of(
            "happy", "grateful", "great", "good", "excellent", "love", "calm", "hopeful",
            "excited", "proud", "peaceful", "joy", "joyful", "amazing", "better", "best",
            "confident", "relaxed", "motivated", "thankful"
    );
    private static final Set<String> NEGATIVE_WORDS = Set.of(
            "sad", "angry", "bad", "terrible", "hate", "anxious", "stressed", "stress",
            "worried", "fear", "afraid", "lonely", "upset", "worse", "worst", "tired",
            "depressed", "frustrated", "overwhelmed", "hurt"
    );

    public int score(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        int score = 0;
        for (String token : WORD_SPLIT.split(text.toLowerCase(Locale.ROOT))) {
            if (POSITIVE_WORDS.contains(token)) {
                score++;
            } else if (NEGATIVE_WORDS.contains(token)) {
                score--;
            }
        }
        return score;
    }

    public Sentiment classify(String text) {
        int score = score(text);
        if (score > 0) {
            return Sentiment.POSITIVE;
        }
        if (score < 0) {
            return Sentiment.NEGATIVE;
        }
        return Sentiment.NEUTRAL;
    }
}
