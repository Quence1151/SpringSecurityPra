package com.example.springsecuritypra.dept2;

import java.util.List;

public interface DeptRepository {
    public List listDept();   // select
    public void insertDept(Dept dto);   // insert
    public void updateDept(Dept dto);	//update
    public void deleteDept(Dept dto);
}
