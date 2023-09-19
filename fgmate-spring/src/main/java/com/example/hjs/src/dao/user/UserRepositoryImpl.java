package com.example.hjs.src.dao.user;

import com.example.hjs.src.domain.refrigeratorGroup.QRefrigeratorGroup;
import com.example.hjs.src.domain.user.QUser;
import com.example.hjs.src.domain.user.QUserDto;
import com.example.hjs.src.domain.user.User;
import com.example.hjs.src.domain.user.UserDto;
import com.example.hjs.src.service.QTableService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;

import java.util.List;


@RequiredArgsConstructor
public class UserRepositoryImpl extends QTableService implements UserRepositoryCustom {

    @Override
    public List<UserDto> getTest() {
        return queryFactory
                .query().select(new QUserDto(qUser))
                .from(qUser)
                .innerJoin(qGroup).on(qGroup.userId.eq(qUser.userId))
                .fetch()
                ;
    }

//    private final JPAQueryFactory jpaQueryFactory;

}
