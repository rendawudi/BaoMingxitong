package com.example.rd.baomingxitong.entity.FileAndShipin;

public class Vedio {
    private String vedioname;   //视频名字
    private Integer spId;       //视频ID
    private Integer vId;        //课程ID
    private String path;        //视频路径

    public String getVedioname() {
        return vedioname;
    }

    public void setVedioname(String vedioname) {
        this.vedioname = vedioname;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
