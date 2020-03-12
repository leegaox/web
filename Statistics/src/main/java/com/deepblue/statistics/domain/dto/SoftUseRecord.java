package com.deepblue.statistics.domain.dto;

import com.deepblue.statistics.domain.po.BasePO;
import lombok.Data;

/**
 * 软件使用记录
 */
@Data
public class SoftUseRecord {

    //记录id
    private int recordId;
    private String remark;
    private String createTime;

    private int softwareId;
    private String softwareName;

    private int employeeId;
    private String employeeName;
    private String jobId;
    private String department;


}
