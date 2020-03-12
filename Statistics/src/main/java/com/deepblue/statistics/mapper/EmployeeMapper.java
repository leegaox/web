package com.deepblue.statistics.mapper;

import com.deepblue.statistics.domain.po.EmployeePO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM t_employee")
    List<EmployeePO> getAllEmployee();

    /**
     * 员工数据新增
     */
    @Insert("insert into t_employee(name,job_id,department) values (#{name},#{jobId},#{department})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean addEmployee(EmployeePO bean);

    @Select("SELECT * FROM t_employee where job_id=#{jobId}")
    EmployeePO queryById(Integer jobId);

}
