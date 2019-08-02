package com.mugon.sortObject;

import com.mugon.domain.Projects;

import java.util.Comparator;

public class AscendingProjects implements Comparator<Projects> {
    @Override
    public int compare(Projects o1, Projects o2) {
        return o1.getIdx().compareTo(o2.getIdx());
    }
}
