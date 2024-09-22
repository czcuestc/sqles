package com.sqles.demo.mapper;

import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.domain.TestEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestEntityMapper {
    long countByExample(TestEntityExample example);

    int deleteByExample(TestEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TestEntity row);

    int insertSelective(TestEntity row);

    List<TestEntity> selectByExample(TestEntityExample example);

    TestEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TestEntity row, @Param("example") TestEntityExample example);

    int updateByExample(@Param("row") TestEntity row, @Param("example") TestEntityExample example);

    int updateByPrimaryKeySelective(TestEntity row);

    int updateByPrimaryKey(TestEntity row);
}