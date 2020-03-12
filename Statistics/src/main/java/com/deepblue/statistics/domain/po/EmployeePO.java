package com.deepblue.statistics.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class EmployeePO extends BasePO {

    public EmployeePO(String name, String jobId, String department) {
        this.name = name;
        this.jobId = jobId;
        this.department = department;
    }

    public EmployeePO(int id, String name, String jobId, String department) {
        setId(id);
        this.name = name;
        this.jobId = jobId;
        this.department = department;
    }

    private String name;
    private String jobId;
    private String department;
}
