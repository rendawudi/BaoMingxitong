package com.example.rd.baomingxitong.entity.LiaoTian;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.example.rd.baomingxitong.GreenDao.DaoSession;
import com.example.rd.baomingxitong.GreenDao.LTMsgDao;
import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao;


/**
 * Created by rd on 2017/11/2.
 */

@Entity
public class LTXiangmu
{
    @Id
    public Long xmid;

    @Index
    public Long xiangmuId;
    @Index
    public Long piciId;
    @Index
    public String duizhangxuehao;
    public  String xiangmu;
    public String duizhangmingzi;
    public String banjimingzi;
    public int zhuangtai;
    @ToMany(referencedJoinProperty = "xmid")
    public List<LTMsg> MsgList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 594436666)
    private transient LTXiangmuDao myDao;
    @Generated(hash = 926126104)
    public LTXiangmu(Long xmid, Long xiangmuId, Long piciId, String duizhangxuehao,
            String xiangmu, String duizhangmingzi, String banjimingzi,
            int zhuangtai) {
        this.xmid = xmid;
        this.xiangmuId = xiangmuId;
        this.piciId = piciId;
        this.duizhangxuehao = duizhangxuehao;
        this.xiangmu = xiangmu;
        this.duizhangmingzi = duizhangmingzi;
        this.banjimingzi = banjimingzi;
        this.zhuangtai = zhuangtai;
    }
    @Generated(hash = 346247735)
    public LTXiangmu() {
    }
    /** Used to resolve relations */

    @Keep
    public Long getXmid() {
        return xmid;
    }
    @Keep
    public void setXmid(Long xmid) {
        this.xmid = xmid;
    }
    @Keep
    public Long getXiangmuId() {
        return xiangmuId;
    }
    @Keep
    public void setXiangmuId(Long xiangmuId) {
        this.xiangmuId = xiangmuId;
    }
    @Keep
    public Long getPiciId() {
        return piciId;
    }
    @Keep
    public void setPiciId(Long piciId) {
        this.piciId = piciId;
    }
    @Keep
    public String getDuizhangxuehao() {
        return duizhangxuehao;
    }
    @Keep
    public void setDuizhangxuehao(String duizhangxuehao) {
        this.duizhangxuehao = duizhangxuehao;
    }
    @Keep
    public String getXiangmu() {
        return xiangmu;
    }
    @Keep
    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }
    @Keep
    public String getDuizhangmingzi() {
        return duizhangmingzi;
    }
    @Keep
    public void setDuizhangmingzi(String duizhangmingzi) {
        this.duizhangmingzi = duizhangmingzi;
    }
    @Keep
    public String getBanjimingzi() {
        return banjimingzi;
    }
    @Keep
    public void setBanjimingzi(String banjimingzi) {
        this.banjimingzi = banjimingzi;
    }
    @Keep
    public int getZhuangtai() {
        return zhuangtai;
    }
    @Keep
    public void setZhuangtai(int zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1423304157)
    public synchronized void resetMsgList() {
        MsgList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1437104267)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLTXiangmuDao() : null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1212978066)
    public List<LTMsg> getMsgList() {
        if (MsgList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LTMsgDao targetDao = daoSession.getLTMsgDao();
            List<LTMsg> MsgListNew = targetDao._queryLTXiangmu_MsgList(xmid);
            synchronized (this) {
                if (MsgList == null) {
                    MsgList = MsgListNew;
                }
            }
        }
        return MsgList;
    }

}
