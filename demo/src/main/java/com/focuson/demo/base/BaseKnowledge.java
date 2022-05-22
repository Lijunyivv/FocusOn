package com.focuson.demo.base;

import lombok.Data;

import java.util.List;

/**
 * @author lijunyi
 * @date 2022/5/21 9:57 下午
 * @Description 知识抽象类
 */
@Data
public abstract class BaseKnowledge {

    /**
     * 等待学习
     */
    public final static Integer WAIT_STUDY = 0;

    /**
     * 在学习中
     */
    public final static Integer STUDYING = 1;

    /**
     * 覆盖到了,只是学过一遍的程度
     */
    public final static Integer IS_COVERED = 2;
    /**
     * 熟练
     */
    public final static Integer SKILLED = 3;

    /**
     * 精通
     */
    public final static Integer KNOWLEDGE_MASTER = 4;


    private Integer status = WAIT_STUDY;

    /**
     * 每学完一个知识都需要写个demo
     */
    public abstract void testDemo();

    /**
     * 相同的,也应该有有一些笔记在这里
     * @return
     */
    public abstract String note();

}
