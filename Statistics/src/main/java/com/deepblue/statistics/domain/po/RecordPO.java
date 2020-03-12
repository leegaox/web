package com.deepblue.statistics.domain.po;

import lombok.Data;

@Data
public class RecordPO extends BasePO {

    public RecordPO(int employeeId, String remark) {
        this.employeeId = employeeId;
        this.remark = remark;
    }

    private int employeeId;
    private String createTime;
    private String remark;
}
