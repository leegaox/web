package com.deepblue.statistics.mapper;

import com.deepblue.statistics.domain.po.SoftwarePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface SoftwareMapper {

    @Select("SELECT * FROM t_software")
    public List<SoftwarePO> getAllSoftware();

}
