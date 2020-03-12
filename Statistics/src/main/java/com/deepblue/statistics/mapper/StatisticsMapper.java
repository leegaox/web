package com.deepblue.statistics.mapper;

import com.deepblue.statistics.domain.dto.SoftUseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StatisticsMapper {

    @Select("SELECT s.software_name,bb.* FROM t_software s LEFT JOIN(SELECT e.name as employee_name,e.department,e.job_id,aa.* FROM t_employee e LEFT JOIN(SELECT r.id as record_id,r.remark,r.create_time,r.employee_id ,rc.software_id FROM `t_record` r LEFT JOIN t_record_common_soft rc ON r.id=rc.record_id ORDER BY rc.software_id) as aa ON e.id=aa.employee_id) AS bb ON s.id=bb.software_id")
    List<SoftUseRecord> getAllSoftUseRecord();
}
