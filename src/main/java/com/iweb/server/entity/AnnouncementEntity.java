package com.iweb.server.entity;

import com.iweb.server.view.MainView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementEntity {
    private Integer aId;
    private Date aDate;
    private String aObject;
    private String aMsg;
    private Integer oId;

    @Override
    public String toString() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(aDate);
        switch (aObject){
            case "E":{
                return "员工公告: "+aMsg+"\t"+date;
            }
            case "C":{
                return "公司公告: "+aMsg+"\t"+date;
            }
            case "D":{
                return "部门公告: "+aMsg+"\t"+date;
            }
        }
        return "公告内容有误";
    }
}
