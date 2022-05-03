package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.StartTab;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

public class StartTabDAO {
    public StartTab create(StartTab startTab){
        startTab.setId(TmpStorage.startTabs.size());
        startTab.setName("Забег" + (startTab.getId() + 1));
        TmpStorage.startTabs.add(startTab);
        return startTab;
    }
}
