package com.example.hjs.src.dao.refrigerator;

import com.example.hjs.src.domain.refrigerator.Refrigerator;
import com.example.hjs.src.domain.refrigerator.RefrigeratorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public
interface  RefrigeratorRepository extends JpaRepository<Refrigerator,Long>, RefrigeratorRepositoryCustom {
    Optional<Refrigerator> findByRefrigeratorId(Long id);
    @Query("select new com.example.hjs.src.domain.refrigerator.RefrigeratorDto(R.refrigeratorName, R.refrigeratorId) " +
            "from Refrigerator R join RefrigeratorGroup G where G.userId =:userId")
    List<RefrigeratorDto> findRefrigeratorByUserId(@Param("userId") Long userId);
}
