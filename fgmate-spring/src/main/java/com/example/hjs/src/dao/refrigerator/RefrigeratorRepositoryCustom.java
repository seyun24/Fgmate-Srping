package com.example.hjs.src.dao.refrigerator;

import com.example.hjs.src.domain.refrigerator.RefrigeratorDto;

import java.util.List;

public interface RefrigeratorRepositoryCustom {
    List<RefrigeratorDto> findRefrigeratorAll(Long userId);
}
