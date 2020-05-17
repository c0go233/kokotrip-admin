package com.kokotripadmin.viewmodel.common;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThemeRelVm {

    private String themeName;
    private int numOfAllTag;

    private List<ThemeTagRelVm> themeTagRelVmList;
}
