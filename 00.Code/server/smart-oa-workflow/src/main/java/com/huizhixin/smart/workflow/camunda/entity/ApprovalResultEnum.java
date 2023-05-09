package com.huizhixin.smart.workflow.camunda.entity;


/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public enum ApprovalResultEnum {
    agree("OK", "同意"), Back("BK", "驳回"), end("end", "结束流程"), submit("ST","提交");
    private String key;
    private String name;


    private ApprovalResultEnum(String key, String name)
    {
        this.key = key;
        this.name = name;
    }


    public String toValue()
    {
        return key;
    }



    /**
     * 检查参数是否在枚举中
     * 
     * @param param 参数
     * @return
     */
    public static boolean check(String param)
    {
        for (ApprovalResultEnum e : ApprovalResultEnum.values())
        {
            if (e.toValue().equals(param))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 通过key得到对应的name
     * 
     * @param index
     * @return
     */
    public static String getName(String key)
    {
        for (ApprovalResultEnum e : ApprovalResultEnum.values())
        {
            if (e.toValue().equals(key))
            {
                return e.name;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
