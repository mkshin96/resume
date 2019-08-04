package com.mugon.sortObject;

import com.mugon.domain.Introduction;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class AscendingIntroduction implements Comparator<Introduction> {
    @Override
    public int compare(Introduction o1, Introduction o2) {
        return o1.getIdx().compareTo(o2.getIdx());
    }
}
