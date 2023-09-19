package com.example.hjs.src.service;


import com.example.hjs.src.domain.refrigerator.QRefrigerator;
import com.example.hjs.src.domain.refrigeratorGroup.QRefrigeratorGroup;
import com.example.hjs.src.domain.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QTableService {
    @Autowired
    protected JPAQueryFactory queryFactory;

    protected QUser qUser = QUser.user;
    protected QRefrigerator qRefri = QRefrigerator.refrigerator;
    protected QRefrigeratorGroup qGroup =QRefrigeratorGroup.refrigeratorGroup;

    public QTableService() {
        super();
    }
}
