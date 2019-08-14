package com.mugon.service;

import com.mugon.domain.Introduction;
import com.mugon.domain.User;
import com.mugon.dto.IntroductionDto;
import com.mugon.repository.IntroductionRepository;
import com.mugon.sortObject.AscendingIntroduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IntroductionService {

    @Autowired
    IntroductionRepository introductionRepository;

    @Autowired
    AscendingIntroduction ascendingIntroduction;

    public void saveIntroduction(IntroductionDto introductionDto, User user) {

        introductionRepository.save(introductionDto.setIntroduction(user));
    }

    public void deleteIntroduction(Long idx) {
        introductionRepository.deleteById(idx);
    }

    //idx순으로 정렬 후 반환
    public List<Introduction> findIntroduction(User user) {
        List<Introduction> introductions = introductionRepository.findByUser(user);

        Collections.sort(introductions, ascendingIntroduction);
        return introductions;
    }


    public void findAndModifiedIntroduction(Long idx, IntroductionDto introductionDto) {
        Introduction modiIntroduction = introductionRepository.getOne(idx);

        modiIntroduction.setTitle(introductionDto.getTitle());
        modiIntroduction.setGrowth(introductionDto.getGrowth());
        modiIntroduction.setReason(introductionDto.getReason());
        modiIntroduction.setStrength((introductionDto.getStrength()));
        modiIntroduction.setWeakness(introductionDto.getWeakness());
        modiIntroduction.setAspiration(introductionDto.getAspiration());

        introductionRepository.save(modiIntroduction);
    }

    public Introduction findModiIntroduction(Long idx) {

        return introductionRepository.getOne(idx);
    }
}
