package com.mugon.sortObject;

import com.mugon.domain.Introduction;

import java.util.Comparator;

public class AscendingIntroduction implements Comparator<Introduction> {
    @Override
    public int compare(Introduction o1, Introduction o2) {
        return o1.getIdx().compareTo(o2.getIdx());
    }
}
