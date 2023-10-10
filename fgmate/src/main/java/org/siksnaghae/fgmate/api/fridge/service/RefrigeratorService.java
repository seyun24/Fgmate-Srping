package org.siksnaghae.fgmate.api.fridge.service;

import lombok.RequiredArgsConstructor;
import org.siksnaghae.fgmate.api.fridge.model.Refrigerator;
import org.siksnaghae.fgmate.api.fridge.model.RefrigeratorDto;
import org.siksnaghae.fgmate.api.fridge.repository.RefrigeratorRepository;
import org.siksnaghae.fgmate.api.group.model.RefrigeratorGroup;
import org.siksnaghae.fgmate.api.group.repository.RefrigeratorGroupRepository;
import org.siksnaghae.fgmate.common.response.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

import static org.siksnaghae.fgmate.common.response.BaseResponseStatus.DATABASE_ERROR;
import static org.siksnaghae.fgmate.common.response.BaseResponseStatus.DUPLICATED_GROUP_USER;

@Service
@RequiredArgsConstructor
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;
    private final RefrigeratorGroupRepository refrigeratorGroupRepository;
    private final EntityManager entityManager;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Long saveRefrigerator(String name, Long userId) throws BaseException {
        try {
            Refrigerator refrigerator = Refrigerator.builder()
                    .refrigeratorName(name)
                    .build();
            Long refrigeratorId = refrigeratorRepository.save(refrigerator).getRefrigeratorId();
            RefrigeratorGroup refrigeratorGroup=RefrigeratorGroup.builder()
                    .userId(userId)
                    .refrigeratorId(refrigeratorId)
                    .build();
            refrigeratorGroupRepository.save(refrigeratorGroup);
            return refrigeratorId ;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<RefrigeratorDto> findAllRefrigerators(Long userId) throws BaseException {
        try {
            return refrigeratorRepository.findRefrigeratorAll(userId);

        } catch (Exception exception) {

            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void modifyRefrigerator(String name, Long id) throws BaseException {
        try {
            Refrigerator refrigerator= Refrigerator.builder()
                    .refrigeratorName(name)
                    .refrigeratorId(id)
                    .build();
            refrigeratorRepository.save(refrigerator);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void deleteRefrigerator(Long id) throws BaseException {
        try {
            Refrigerator refrigerator = refrigeratorRepository.findByRefrigeratorId(id).orElse(null);
            entityManager.remove(refrigerator);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void inviteGroup(Long id, Long userId) throws BaseException {
        if (refrigeratorGroupRepository.existsByRefrigeratorIdAndUserId(id,userId)) {
            throw new BaseException(DUPLICATED_GROUP_USER);
        }
        try {
            RefrigeratorGroup refrigeratorGroup=RefrigeratorGroup.builder()
                    .userId(userId)
                    .refrigeratorId(id)
                    .build();
            refrigeratorGroupRepository.save(refrigeratorGroup);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void deleteGroupUser(Long id, Long userId) throws BaseException {
        try {
            RefrigeratorGroup refrigeratorGroup = refrigeratorGroupRepository.findByRefrigeratorIdAndUserId(id,userId).orElse(null);
            entityManager.remove(refrigeratorGroup);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
