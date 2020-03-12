package com.deepblue.statistics.domain.dto;

import com.deepblue.statistics.domain.po.SoftwarePO;
import lombok.Data;

import java.util.List;

/**
 * 网页统计数据
 */
@Data
public class Record {
    private int id;
    private String employeeName;
    private String jobId;
    private String department;
    private String remark;
    private List<SoftwarePO> softwareList;

}
