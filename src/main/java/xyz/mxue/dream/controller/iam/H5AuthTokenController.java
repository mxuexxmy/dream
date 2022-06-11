package xyz.mxue.dream.controller.iam;

import com.diboot.core.controller.BaseController;
import com.diboot.iam.annotation.Log;
import com.diboot.core.vo.*;
import com.diboot.iam.auth.AuthServiceFactory;
import com.diboot.iam.config.Cons;
import com.diboot.iam.dto.PwdCredential;
import com.diboot.iam.util.IamSecurityUtils;
import com.diboot.mobile.entity.IamMember;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
* H5登录申请Token接口
* @author mxuexxmy
* @version 1.0
* @date 2022-06-11
* Copyright © mxue
*/
@Slf4j
@RestController
@RequestMapping("/h5")
public class H5AuthTokenController extends BaseController {

    /**
    * 用户登录获取token
    * @param credential
    * @return
    * @throws Exception
    */
    @PostMapping("/auth/login")
    public JsonResult login(@RequestBody PwdCredential credential) throws Exception{
        credential.setAuthType(Cons.DICTCODE_AUTH_TYPE.PWD.name()).setUserTypeClass(IamMember.class);
        String authtoken = AuthServiceFactory.getAuthService(Cons.DICTCODE_AUTH_TYPE.PWD.name()).applyToken(credential);
        return JsonResult.OK(authtoken);
    }

    /**
    * 注销/退出
    * @return
    * @throws Exception
    */
		@Log(businessObj = "LoginUser", operation = "退出")
    @PostMapping("/logout")
    public JsonResult logout() throws Exception{
        IamSecurityUtils.logout();
        return JsonResult.OK();
    }

    /**
    * 获取用户信息
    * @return
    */
    @GetMapping("/userInfo")
    public JsonResult getUserInfo(){
        // 获取当前登录用户对象
        Object currentUser = IamSecurityUtils.getCurrentUser();
        if(currentUser == null){
            return JsonResult.OK();
        }
        return JsonResult.OK(currentUser);
    }

}