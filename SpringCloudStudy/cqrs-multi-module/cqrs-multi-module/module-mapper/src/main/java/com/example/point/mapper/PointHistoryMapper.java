package com.example.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.point.dto.PointHistoryDTO;

@Mapper
public interface PointHistoryMapper {
  List<PointHistoryDTO> findPointHistoryByMemberId(Long memberId);
}
