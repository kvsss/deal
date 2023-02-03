package com.deng.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.dao.entity.GoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023/02/04
 */
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    /**
     * 商品搜索
     * @param page mybatis-plus 分页对象
     * @param condition 搜索条件
     * @return 返回结果
     * */
    List<GoodsInfo> searchGoods(IPage<GoodsInfoRespDTO> page, GoodsSearchReqDTO condition);
}
