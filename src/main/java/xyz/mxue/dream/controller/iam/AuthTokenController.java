package xyz.mxue.dream.controller.iam;

import com.diboot.core.controller.BaseController;
import com.diboot.iam.annotation.BindPermission;
import com.diboot.iam.annotation.Log;
import com.diboot.core.vo.*;
import com.diboot.iam.auth.AuthServiceFactory;
import com.diboot.iam.config.Cons;
import com.diboot.iam.dto.PwdCredential;
import com.diboot.iam.entity.BaseLoginUser;
import com.diboot.iam.service.IamUserRoleService;
import com.diboot.iam.util.IamSecurityUtils;
import com.diboot.iam.vo.IamRoleVO;
import com.pig4cloud.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
* IAM身份认证/申请Token接口
* @author mxuexxmy
* @version 1.0
* @date 2022-06-11
* Copyright © mxue
*/
@RestController
@Slf4j
@BindPermission(name = "登录认证", code = "AUTH")
public class AuthTokenController extends BaseController {
    @Autowired
    private IamUserRoleService iamUserRoleService;
    
    private static final String CAPTCHA_KEY = "captcha";

    /**
    * 获取验证码
    */
    @GetMapping("/auth/captcha")
    public void captcha(HttpServletResponse response) throws Exception {
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 算数验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        // 验证码存入session
        request.getSession().setAttribute(CAPTCHA_KEY, captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(response.getOutputStream());
    }

    /**
    * 用户登录获取token
    * @param credential
    * @return
    * @throws Exception
    */
    @PostMapping("/auth/login")
    public JsonResult login(@RequestBody PwdCredential credential) throws Exception{
        // 获取session中的验证码
        String captcha = (String) request.getSession().getAttribute(CAPTCHA_KEY);
        request.getSession().removeAttribute(CAPTCHA_KEY);
        String verCode = credential.getCaptcha();
        // 判断验证码
        if (verCode == null || !verCode.trim().toLowerCase().equals(captcha)) {
            return JsonResult.FAIL_VALIDATION("验证码错误");
        }
        return JsonResult.OK(AuthServiceFactory.getAuthService(Cons.DICTCODE_AUTH_TYPE.PWD.name()).applyToken(credential));
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
    * 获取用户角色权限信息
    * @return
    */
    @GetMapping("/auth/userInfo")
    public JsonResult getUserInfo(){
        Map<String, Object> data = new HashMap<>();
        // 获取当前登录用户对象
        BaseLoginUser currentUser = IamSecurityUtils.getCurrentUser();
        if(currentUser == null){
            return JsonResult.OK();
        }
        data.put("name", currentUser.getDisplayName());
        // 角色权限数据
        IamRoleVO roleVO = iamUserRoleService.buildRoleVo4FrontEnd(currentUser);
        data.put("role", roleVO);
        return JsonResult.OK(data);
    }

    /***
    * 心跳接口
    * @return
    * @throws Exception
    */
    @PostMapping("/iam/ping")
    public JsonResult ping() throws Exception{
        return JsonResult.OK();
    }
}