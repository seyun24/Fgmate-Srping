package com.example.hjs.src.dao.refrigerator;


import com.example.hjs.src.domain.refrigerator.QRefrigeratorDto;
import com.example.hjs.src.domain.refrigerator.RefrigeratorDto;
import com.example.hjs.src.service.QTableService;

import java.util.List;

public class RefrigeratorRepositoryImpl extends QTableService implements RefrigeratorRepositoryCustom {
    @Override
    public List<RefrigeratorDto> findRefrigeratorAll(Long userId) {
        return queryFactory.query()
                .select(new QRefrigeratorDto(
                        qRefri.refrigeratorName,
                        qRefri.refrigeratorId))
                .from(qRefri)
                .innerJoin(qGroup).on(qGroup.refrigeratorId.eq(qRefri.refrigeratorId))
                .where(qGroup.userId.eq(userId))
                .fetch();
    }
}
