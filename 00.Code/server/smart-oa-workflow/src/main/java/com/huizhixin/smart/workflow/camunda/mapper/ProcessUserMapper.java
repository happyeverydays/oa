package com.huizhixin.smart.workflow.camunda.mapper;

import com.huizhixin.smart.workflow.camunda.entity.ProcessUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProcessUserMapper {
    public List<ProcessUser> getProcessUserList();
    
    public List<ProcessUser> getProcessGroupList();

    public List<ProcessUser> getProcessDepartList();
    
    public List<String> getUserIds(@Param("departIdList") List<String> departIdList, @Param("roleIdList")List<String> roleIdList );
}
