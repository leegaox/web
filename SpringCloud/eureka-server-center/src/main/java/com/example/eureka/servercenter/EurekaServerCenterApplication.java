package com.example.eureka.servercenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//申明是一个Eureka服务器
@EnableEurekaServer
@SpringBootApplication
@RestController
@Slf4j
public class EurekaServerCenterApplication {


    public static void main(String[] args) {
        SpringApplication.run(EurekaServerCenterApplication.class, args);
    }


    @GetMapping(value = "/test")
    public ResponseEntity<byte[]> Hello() {
        ExcelData data = exportTest();
        return generateHttpEntity(ExcelUtil.readDataAsByteArray(data), data.getFileName(), ".xlsx");
    }

    @GetMapping(value = "/getImgCode")
    public ImgCodeVo testImage(HttpServletRequest request, HttpServletResponse response) {
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        // 输出图片方法
        ImgCodeVo imgCodeVo = randomValidateCode.getRandcode(request, response);
        if (imgCodeVo != null) {
            log.info("get imgCode:" + imgCodeVo.getRand());
            String imgKey = CommonUtil.getUUID();
            imgCodeVo.setRand("");
            imgCodeVo.setImgKey(imgKey);
            return imgCodeVo;
        }
        return null;
    }


    public ResponseEntity<byte[]> generateHttpEntity(byte[] bytes, String fileName, String suffix) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setCacheControl("max-age=604800");
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName + suffix, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }


    public ExcelData exportTest() {
        ExcelData data = new ExcelData();
        String time = "2020-05-09";
        String fileName = "temp" + time;
        data.setFileName(fileName);
        data.setSheetName("temp");
        List<String> titles = new ArrayList();
        titles.add("设备id");
        titles.add("上报温度");
        titles.add("是否发烧");
        titles.add("上报时间");
        titles.add("照片地址");
        titles.add("是否处理");
        titles.add("处理人");
        titles.add("处理时间");
        titles.add("人员id");
        titles.add("原始体温");
        titles.add("是否误报");
        titles.add("二次测量体温");
        titles.add("确认时间");
        titles.add("确认用户");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for (int i = 0, length = 10; i < length; i++) {
            List<Object> row = new ArrayList();
            row.add(1);
            row.add(33);
            row.add("是");
            row.add("2020-05-09 08:01:11");
            row.add(123);
            row.add(1);
            row.add(1);
            row.add("");
            row.add(1);
            row.add(36);
            row.add("否");
            //对于不是高温的人员，二次测量体温为“无” 或者 没上报二次测量体温时显示“无”
            row.add("35℃");
            row.add("");
            row.add("LEE");
            rows.add(row);
        }
        data.setRows(rows);
        return data;
    }
}
