package xyz.mxue.dream.controller;

import com.diboot.core.vo.JsonResult;
import com.diboot.core.vo.LabelValue;
import com.diboot.iam.entity.SystemConfig;
import com.diboot.iam.service.SystemConfigService;
import com.diboot.iam.vo.SystemConfigVO;
import com.diboot.iam.annotation.BindPermission;
import com.diboot.iam.annotation.Log;
import com.diboot.iam.annotation.OperationCons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置Controller
 *
 * @author mxuexxmy
 * @version 1.0
 * @date 2022-06-11
 * Copyright © mxue
 */
@BindPermission(name = "系统配置")
@RestController
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseCustomCrudRestController<SystemConfig> {

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 获取系统配置类型列表
     *
     * @return
     */
    @Log(operation = OperationCons.LABEL_LIST)
    @BindPermission(name = OperationCons.LABEL_LIST, code = OperationCons.CODE_LIST)
    @GetMapping("/typeList")
    public JsonResult<List<LabelValue>> getTypeListMapping() {
        return JsonResult.OK(systemConfigService.getTypeList());
    }

    /**
     * 获取指定类型的系统配置信息
     *
     * @param type 类型
     * @return
     */
    @Log(operation = OperationCons.LABEL_DETAIL)
    @BindPermission(name = OperationCons.LABEL_DETAIL, code = OperationCons.CODE_DETAIL)
    @GetMapping("/{type}")
    public JsonResult<List<SystemConfigVO>> getConfigByTypeMapping(@PathVariable String type) {
        return JsonResult.OK(systemConfigService.getConfigByType(type));
    }

    /**
     * 更新系统配置
     *
     * @param systemConfig
     * @return
     */
    @Log(operation = OperationCons.LABEL_UPDATE)
    @BindPermission(name = OperationCons.LABEL_UPDATE, code = OperationCons.CODE_UPDATE)
    @PostMapping
    public JsonResult<?> updateConfigMapping(@RequestBody SystemConfig systemConfig) {
        return new JsonResult<>(systemConfigService.createOrUpdateEntity(systemConfig));
    }

    /**
     * 重置指定类型或属性的系统配置
     *
     * @param type 类型
     * @param prop 属性（为空重置整个类型）
     * @return
     */
    @Log(operation = "重置")
    @BindPermission(name = "重置", code = OperationCons.CODE_UPDATE)
    @DeleteMapping({"/{type}/{prop}", "/{type}"})
    public JsonResult<?> deleteTypeMapping(@PathVariable String type, @PathVariable(required = false) String prop) {
        systemConfigService.deleteByTypeAndProp(type, prop);
        return JsonResult.OK();
    }

    /**
     * 系统配置测试
     *
     * @param type 类型
     * @param data 数据
     * @return
     */
    @Log(operation = "测试")
    @BindPermission(name = "测试", code = OperationCons.CODE_UPDATE)
    @PostMapping("/{type}")
    public JsonResult<?> configTestMapping(@PathVariable String type, @RequestBody Map<String, Object> data) {
        systemConfigService.configTest(type, data);
        return JsonResult.OK();
    }

}
