package com.mugon.service;

import com.mugon.sortObject.AscendingIntroduction;
import com.mugon.domain.Introduction;
import com.mugon.repository.IntroductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class IntroductionService {

    @Autowired
    IntroductionRepository introductionRepository;

    @Autowired
    AscendingIntroduction ascendingIntroduction;

    public void saveIntroduction(Introduction introduction) {
        introduction.setRegisteredDate(LocalDateTime.now());
        introductionRepository.save(introduction);
    }

    public void deleteIntroduction(Long idx) {
        introductionRepository.deleteById(idx);
    }

    //idx순으로 정렬 후 반환
    public List<Introduction> findIntroduction() {
        List<Introduction> introductions = introductionRepository.findAll();
        Collections.sort(introductions, ascendingIntroduction);
        return introductions;
    }


    public void findAndModifiedIntroduction(Long idx, Introduction introduction) {
        Introduction modiIntroduction = introductionRepository.getOne(idx);

        modiIntroduction.setTitle(introduction.getTitle());
        modiIntroduction.setGrowth(introduction.getGrowth());
        modiIntroduction.setReason(introduction.getReason());
        modiIntroduction.setStrength((introduction.getStrength()));
        modiIntroduction.setWeakness(introduction.getWeakness());
        modiIntroduction.setAspiration(introduction.getAspiration());

        introductionRepository.save(modiIntroduction);
    }

    public Introduction findModiIntroduction(Long idx) {

        return introductionRepository.getOne(idx);
    }
}
