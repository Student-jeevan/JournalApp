package com.spring.journalApp.Cache;

import com.spring.journalApp.entity.ConfigEntity;
import com.spring.journalApp.repository.ConfigjournalRepo;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class appCache{
    public enum keys{
        WEATHER_API;

    }
    public  Map<String , String> App_Cache = new HashMap<>();
    @Autowired
    private ConfigjournalRepo configrepo;
    @PostConstruct
    public void init() {
         List<ConfigEntity> all = configrepo.findAll();
         for(ConfigEntity configEntity : all) {
             App_Cache.put(configEntity.getKey(), configEntity.getValue());
         }
        System.out.println("App Cache Initialized: " + App_Cache);
    }

}
