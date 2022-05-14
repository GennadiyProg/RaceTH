package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.StartTab;
import com.lifehouse.raceth.repository.StartTabRepository;
import org.springframework.stereotype.Component;

@Component
public class StartTabDAO {
    private final StartTabRepository startTabRepository;

    public StartTabDAO() {
        this.startTabRepository = (StartTabRepository) Main.appContext.getBean("startTabRepository");
    }

    public StartTab create(StartTab startTab){
        startTab.setId(startTabRepository.count() + 1);
        startTab.setName("Забег" + (startTab.getId() + 1));
        return startTabRepository.save(startTab);
    }
}
