package org.siksnaghae.fgmate.api.fridge.repository;



import org.siksnaghae.fgmate.api.fridge.model.QRefrigeratorDto;
import org.siksnaghae.fgmate.api.fridge.model.RefrigeratorDto;
import org.siksnaghae.fgmate.common.QTableService;

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
