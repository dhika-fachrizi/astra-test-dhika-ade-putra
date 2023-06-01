package com.dhika.astralife.service;

import java.util.Date;
import java.util.List;

import com.dhika.astralife.entity.TitleEntity;
import com.dhika.astralife.model.TitleModel;

public interface TitleService {
    TitleEntity createTitleByEmployee(Integer employeeId, TitleModel request);

    List<TitleEntity> getTitlesByEmployee(Integer employeeId);

    TitleEntity getCurrentTitleByEmployee(Integer employeeId);

    TitleEntity updateTitleByEmployee(Integer employeeId, TitleModel request);

    void deleteCurrentTitle(Integer employeeId);
}
