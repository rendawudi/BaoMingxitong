package com.example.rd.baomingxitong.entity.FileAndShipin;


public class Wendang {
    private String weizhi;  //文档路径
    private Integer xiangmuId;  //项目ID
    private Integer piciId;     //班次ID
    private String mingcheng;   //文档名字
    private Integer sfTP;       //是否视频
    private Integer wjId;       //文件ID


    public Integer getWjId() {
        return wjId;
    }

    public void setWjId(Integer wjId) {
        this.wjId = wjId;
    }

    public Integer getSfTP()
    {
        return sfTP;
    }
    public void setSfTP(Integer sfTP)
    {
        this.sfTP = sfTP;
    }
    public String getMingcheng()
    {
        return mingcheng;
    }
    public void setMingcheng(String mingcheng)
    {
        this.mingcheng = mingcheng;
    }
    public String getWeizhi()
    {
        return weizhi;
    }
    public void setWeizhi(String weizhi)
    {
        this.weizhi = weizhi;
    }

    public Integer getXiangmuId() {
        return xiangmuId;
    }

    public void setXiangmuId(Integer xiangmuId) {
        this.xiangmuId = xiangmuId;
    }

    public Integer getPiciId() {
        return piciId;
    }

    public void setPiciId(Integer piciId) {
        this.piciId = piciId;
    }
}
