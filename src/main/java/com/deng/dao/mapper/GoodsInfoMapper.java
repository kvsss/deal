package com.deng.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {
    /**
     *商品查询
     * @param page 分页信息
     * @param condition 查询条件
     * @return 返回查询结果
     */
    List<GoodsInfo> searchGoods(IPage<GoodsInfoRespDTO> page, GoodsSearchReqDTO condition);

}
