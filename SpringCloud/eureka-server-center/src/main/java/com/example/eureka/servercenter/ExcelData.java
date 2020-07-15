package com.example.eureka.servercenter;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author gaox
 * @since 2020/3/23
 */
@Data
public class ExcelData<T> implements Serializable {
    private static final long serialVersionUID = 4444017239100620999L;

    //直接下载的情况下生成文件存放路径
    private String savePath;

    // 文件名称
    private String fileName;

    // 表头
    private List<String> titles;

    // 数据
    private List<List<T>> rows;

    // 页签名称
    private String sheetName;

}
