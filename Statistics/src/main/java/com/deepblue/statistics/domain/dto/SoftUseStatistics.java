package com.deepblue.statistics.domain.dto;

import com.deepblue.statistics.domain.po.EmployeePO;
import lombok.Data;

import java.util.List;

/**
 * 软件使用统计实体bean
 */
@Data
public class SoftUseStatistics {

    private String softwareName;

    private int softwareId;

    List<EmployeePO> employeeList;

}
