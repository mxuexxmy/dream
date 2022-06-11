package xyz.mxue.dream.service.impl;

import com.diboot.core.service.impl.BaseServiceImpl;
import com.diboot.core.mapper.BaseCrudMapper;
import xyz.mxue.dream.service.BaseCustomService;
import lombok.extern.slf4j.Slf4j;
/**
* 自定义 BaseService 接口实现
* @author mxuexxmy
* @version 1.0
* @date 2022-06-11
 * Copyright © mxue
*/
@Slf4j
public class BaseCustomServiceImpl<M extends BaseCrudMapper<T>, T> extends BaseServiceImpl<M, T> implements BaseCustomService<T> {

}
