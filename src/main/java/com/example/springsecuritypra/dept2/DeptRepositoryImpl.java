package com.example.springsecuritypra.dept2;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DeptRepositoryImpl implements DeptRepository{
    private JdbcTemplate jdbcTemplate;  // DML - insert, update, delete, select

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {   // DI - setter method
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List listDept() {   // select
        String sql = "SELECT * FROM dept2 ORDER BY DCODE DESC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public void insertDept(Dept dto) {  // insert
        String sql ="INSERT INTO dept2 (DCODE,DNAME,PDEPT,AREA) VALUES (?, ?, ?, ?)";
        Object[] arr = { dto.getDCODE(), dto.getDNAME(), dto.getPDEPT(), dto.getAREA() };

        this.jdbcTemplate.update(sql, arr);
    }

    @Override
    public void updateDept(Dept dto) {
        String sql = "update DEPT2 set DCODE=? where DCODE=?";
        Object[] arr = { dto.getDCODE(),dto.getDCODE()};
        this.jdbcTemplate.update(sql, arr);

    }

    @Override
    public void deleteDept(Dept dto) {
        String sql = "delete from users where DCODE = ?";
        Object[] arr = {dto.getDCODE()};
        this.jdbcTemplate.update(sql, arr);
    }
}
