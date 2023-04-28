package com.deng.core.common.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description : 分页数据返回
 * @since :1.8
 */
@Getter
public class PageRespDTO<T> {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private final long pageNum;

    /**
     * 每页大小
     */
    @Schema(description = "每页大小")
    private final long pageSize;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private final long total;

    /**
     * 分页数据集合
     */
    @Schema(description = "分页数据集合")
    private final List<? extends T> list;

    /**
     * 该构造函数用于通用分页查询的场景 接收普通分页数据和普通集合
     */
    public PageRespDTO(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    // 快速生成
    public static <T> PageRespDTO<T> of(long pageNum, long pageSize, long total, List<T> list) {
        return new PageRespDTO<>(pageNum, pageSize, total, list);
    }

    /**
     * @return : 获取分页数
     */
    public long getPages() {
        if (this.pageSize == 0L) {
            return 0L;
        } else {
            long pages = this.total / this.pageSize;
            if (this.total % this.pageSize != 0L) {
                ++pages;
            }
            return pages;
        }
    }

    @Override
    public String toString() {
        return "PageRespDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
