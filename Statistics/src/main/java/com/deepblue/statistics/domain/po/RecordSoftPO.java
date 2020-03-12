package com.deepblue.statistics.domain.po;

import lombok.Data;

/**
 * 记录和软件关系实体
 */
@Data
public class RecordSoftPO extends BasePO {
    public RecordSoftPO(int recordId, int softwareId) {
        this.recordId = recordId;
        this.softwareId = softwareId;
    }

    private int recordId;

    private int softwareId;
}
