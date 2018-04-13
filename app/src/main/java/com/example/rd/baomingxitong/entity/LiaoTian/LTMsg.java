package com.example.rd.baomingxitong.entity.LiaoTian;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by rd on 2017/11/2.
 */

@Entity
public class LTMsg
{
    @Id
    public Long id;
    public Long xmid;
    public String sender;
    public Long xiangmuId;
    public String msg;
    public String time;
    public String xingming;
    public String duizhangxuehao;
    public Long piciId;
    @Generated(hash = 1547363157)
    public LTMsg(Long id, Long xmid, String sender, Long xiangmuId, String msg,
            String time, String xingming, String duizhangxuehao, Long piciId) {
        this.id = id;
        this.xmid = xmid;
        this.sender = sender;
        this.xiangmuId = xiangmuId;
        this.msg = msg;
        this.time = time;
        this.xingming = xingming;
        this.duizhangxuehao = duizhangxuehao;
        this.piciId = piciId;
    }
    @Generated(hash = 604342249)
    public LTMsg() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public Long getXiangmuId() {
        return this.xiangmuId;
    }
    public void setXiangmuId(Long xiangmuId) {
        this.xiangmuId = xiangmuId;
    }
    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getXingming() {
        return this.xingming;
    }
    public void setXingming(String xingming) {
        this.xingming = xingming;
    }
    public Long getXmid() {
        return this.xmid;
    }
    public void setXmid(Long xmid) {
        this.xmid = xmid;
    }
    public String getDuizhangxuehao() {
        return this.duizhangxuehao;
    }
    public void setDuizhangxuehao(String duizhangxuehao) {
        this.duizhangxuehao = duizhangxuehao;
    }
    public Long getPiciId() {
        return this.piciId;
    }
    public void setPiciId(Long piciId) {
        this.piciId = piciId;
    }

   
}
