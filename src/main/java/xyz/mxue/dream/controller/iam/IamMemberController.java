package xyz.mxue.dream.controller.iam;  

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diboot.core.controller.BaseCrudRestController;
import com.diboot.core.vo.JsonResult;
import com.diboot.core.vo.Pagination;
import com.diboot.iam.annotation.Log;
import com.diboot.iam.annotation.OperationCons;
import com.diboot.mobile.entity.IamMember;
import com.diboot.mobile.dto.IamMemberDTO;
import com.diboot.mobile.vo.IamMemberVO;
import com.diboot.mobile.service.IamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * 移动端用户controller
 * @author mxuexxmy
 * @version 1.0
 * @date 2022-06-11
 * Copyright © mxue
 */
@RestController
@RequestMapping("/iam/member")
@Slf4j
public class IamMemberController extends BaseCrudRestController<IamMember> {
    
    @Autowired
    private IamMemberService iamMemberService;

    /**
     * 查询ViewObject的分页数据
     * <p>
     * url请求参数示例: /list?field=abc&pageSize=20&pageIndex=1&orderBy=id
     * </p>
     *
     * @return
     * @throws Exception
     */
    @Log(operation = OperationCons.LABEL_LIST)
    @GetMapping("/list")
    public JsonResult getViewObjectListMapping(IamMemberDTO dto, Pagination pagination) throws Exception{
        QueryWrapper<IamMember> queryWrapper = super.buildQueryWrapperByDTO(dto);
        // 查询当前页的数据
        List<IamMemberVO> voList = iamMemberService.getViewObjectList(queryWrapper, pagination, IamMemberVO.class);
        // 返回结果
        return JsonResult.OK(voList).bindPagination(pagination);
    }

    /**
     * 根据资源id查询ViewObject
     *
     * @param id ID
     * @return
     * @throws Exception
     */
    @Log(operation = OperationCons.LABEL_DETAIL)
    @GetMapping("/{id}")
    public JsonResult getViewObjectMapping(@PathVariable("id")Long id) throws Exception{
        return super.getViewObject(id, IamMemberVO.class);
    }

    /**
     * 更新移动端用户
     *
     * @param id
     * @param entity
     * @return JsonResult
     * @throws Exception
     */
    @Log(operation = OperationCons.LABEL_UPDATE)
    @PutMapping("/{id}")
    public JsonResult updateEntityMapping(@PathVariable("id") Long id, @Valid @RequestBody IamMember entity) throws Exception {
        return super.updateEntity(id, entity);
    }

}
