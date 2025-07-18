package com.spring.journalApp.Shedular;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.entity.journalentry;
import com.spring.journalApp.enums.Sentiment;
import com.spring.journalApp.repository.UserRepo;
import com.spring.journalApp.repository.userRepoimpl;
import com.spring.journalApp.repository.journalentryrepo;
import com.spring.journalApp.service.EmailService;
import com.spring.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserShedular {
    @Autowired
    private EmailService emailService;
    @Autowired
    private userRepoimpl userreposit;
    @Autowired
    private SentimentAnalysisService sentimentService;
    @Autowired
    private journalentryrepo journalEntryRepo;
    // @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchAndSendMain(){
        List<User> users = userreposit.getUserforSA();
        for (User user : users) {
            List<journalentry> journalEntries = user.getJournals();
            for (journalentry entry : journalEntries) {
                if (entry.getSentiment() == null) {
                    String sentimentStr = sentimentService.getSentiment(entry.getTitle() + " " + entry.getContent());
                    Sentiment sentiment = null;
                    try {
                        sentiment = Sentiment.valueOf(sentimentStr);
                    } catch (Exception e) {
                        // fallback or log
                    }
                    entry.setSentiment(sentiment);
                    journalEntryRepo.save(entry);
                }
            }
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendmail(user.getEmail(), "Sentiment for previous week", mostFrequentSentiment.toString());
            }
        }
    }
}
