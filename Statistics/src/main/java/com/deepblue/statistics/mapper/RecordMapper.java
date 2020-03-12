package com.deepblue.statistics.mapper;

import com.deepblue.statistics.domain.po.RecordPO;
import com.deepblue.statistics.domain.po.RecordSoftPO;
import com.deepblue.statistics.domain.dto.SoftUseRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface RecordMapper {

    @Select("SELECT a.*,b.name,b.job_id,b.department FROM t_record  a LEFT JOIN t_employee b ON b.id= a.employee_id")
    List<RecordPO> getAllRecord();


    /**
     * 查詢是否已经提交问卷
     *
     * @param jobId
     * @return
     */
    @Select("SELECT count(*) FROM (SELECT a.* FROM t_record a LEFT JOIN  t_employee b ON a.employee_id=b.id WHERE b.job_id=#{jobId}) aa")
    int queryRecordNum(String jobId);

    /**
     * 新增记录
     */
    @Insert("insert into t_record(remark,employee_id) values (#{remark},#{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean addRecord(RecordPO record);


    /**
     * 向记录软件关系表中批量插入数据
     *
     * @param list
     * @return
     */
    @Insert({
            "<script>",
            "insert into t_record_common_soft(record_id, software_id) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.recordId}, #{item.softwareId})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean insertCollectionList(List<RecordSoftPO> list);


}
