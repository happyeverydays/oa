package com.huizhixin.smart.modules.device.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huizhixin.integrated.cameradevice.entity.HeartbeatModel;
import com.huizhixin.integrated.cameradevice.entity.NotifyResult;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.entity.AttendRecord;
import com.huizhixin.integrated.service.IAttendRecordService;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.DateTimeUtil;
import com.huizhixin.smart.modules.system.entity.SysUser;
import com.huizhixin.smart.modules.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 区域
 * @Author: jeecg-boot
 * @Date: 2019-09-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "设备监听")
@RestController
@RequestMapping("/cameraListen")
public class CameraListenController
{
    @Autowired
    private IAttendRecordService attendRecordService;
    
    @Autowired
    private ISysUserService sysUserService;

    
    /**
     * 心跳请求
     *
     * @param heartbeatModel
     * @return
     */
    @AutoLog(value = "设备监听-心跳请求")
    @ApiOperation(value = "设备监听-心跳请求", notes = "设备监听-心跳请求")
    @PostMapping(value = "/heartbeat")
    public ResultInfo<Object> heartbeat(@RequestBody HeartbeatModel heartbeatModel)
    {
        ResultInfo<Object> result = new ResultInfo<Object>();
        
        result.setResult(0);
        result.setMessage("Success");
        return result;
    }
    
    
    @AutoLog(value = "设备监听-识别结果")
    @ApiOperation(value = "设备监听-识别结果", notes = "设备监听-识别结果")
    @PostMapping(value = "/notify")
    public ResultInfo<Object> notify(@RequestBody NotifyResult notifyResult)
    {
        
        System.out.println(notifyResult);
        
        ResultInfo<Object> result = new ResultInfo<Object>();
        SysUser user = sysUserService.getUserByRealname(notifyResult.getName());
        
        // 添加刷卡记录
        if (user != null)
        {
            Date recordDate = DateTimeUtil.formatStringToDate(notifyResult.getTime(), DateTimeUtil.DATE_FARMAT);
            
            AttendRecord record = new AttendRecord();
            record.setUserid(String.valueOf(user.getId()));
            record.setUsername(user.getRealname());
            
            // 设置人员部门信息
            AttendRecord data = attendRecordService.getDataByUserId(user.getId());
            record.setDeptId(data.getDeptId());
            record.setDeptName(data.getDeptName());
            record.setAttendType(data.getAttendType());
            
            record.setAttendWay("3");
            record.setStartTime(recordDate);
            attendRecordService.save(record);
        }
        
        result.setResult(0);
        result.setMessage("Success");
        return result;
    }
    
}
