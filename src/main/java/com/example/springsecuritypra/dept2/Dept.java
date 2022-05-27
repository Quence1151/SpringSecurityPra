package com.example.springsecuritypra.dept2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Dept {
    private int DCODE;
    private String DNAME;
    private int PDEPT;
    private String AREA;
}
