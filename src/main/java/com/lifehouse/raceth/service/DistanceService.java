package com.lifehouse.raceth.service;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.view.DistanceView;
import com.lifehouse.raceth.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("distanceService")
public class DistanceService {
    @Autowired
    private DistanceRepository distanceRepository;

    public List<DistanceView> findAll(){
        return distanceRepository.findAll().stream().map(DistanceView::convertToView).toList();
    }
}
