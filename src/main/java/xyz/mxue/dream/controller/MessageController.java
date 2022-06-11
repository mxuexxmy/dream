package xyz.mxue.dream.controller;

import com.diboot.core.controller.BaseCrudRestController;
import com.diboot.core.vo.JsonResult;
import com.diboot.core.vo.Pagination;
import com.diboot.iam.annotation.BindPermission;
import com.diboot.iam.annotation.Log;
import com.diboot.iam.annotation.OperationCons;
import com.diboot.message.dto.MessageDTO;
import com.diboot.message.entity.Message;
import com.diboot.message.service.MessageService;
import com.diboot.message.vo.MessageDetailVO;
import com.diboot.message.vo.MessageListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息 相关Controller
 *
 * @author mxuexxmy
 * @version 1.0
 * @date 2022-06-11
 * * Copyright © mxue
 */
@RestController
@RequestMapping("/message")
@BindPermission(name = "消息通知")
@Slf4j
public class MessageController extends BaseCrudRestController<Message> {
    @Autowired
    private MessageService messageService;

    /**
     * 查询ViewObject的分页数据
     * <p>
     * url请求参数示例: /list?field=abc&pageIndex=1&orderBy=abc:DESC
     * </p>
     *
     * @return
     * @throws Exception
     */
    @Log(operation = OperationCons.LABEL_LIST)
    @BindPermission(name = OperationCons.LABEL_LIST, code = OperationCons.CODE_LIST)
    @GetMapping("/list")
    public JsonResult getViewObjectListMapping(MessageDTO queryDto, Pagination pagination) throws Exception {
    return super.getViewObjectList(queryDto, pagination, MessageListVO.class);
    }

    /**
     * 根据资源id查询ViewObject
     *
     * @param id ID
     * @return
     * @throws Exception
     */
    @Log(operation = OperationCons.LABEL_DETAIL)
    @BindPermission(name = OperationCons.LABEL_DETAIL, code = OperationCons.CODE_DETAIL)
    @GetMapping("/{id}")
    public JsonResult getViewObjectMapping(@PathVariable("id") Long id) throws Exception {
        return super.getViewObject(id, MessageDetailVO.class);
    }

}