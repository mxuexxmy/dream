package xyz.mxue.dream.controller.iam;

import com.diboot.core.controller.BaseController;
import com.diboot.core.vo.JsonResult;
import com.diboot.iam.auth.AuthServiceFactory;
import com.diboot.iam.config.Cons;
import com.diboot.mobile.entity.IamMember;
import com.diboot.mobile.dto.MobileCredential;
import com.diboot.mobile.dto.WxMemberDTO;
import com.diboot.mobile.service.WxMaAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
* 微信小程序登陆/退出接口
* @author mxuexxmy
* @version 1.0
* @date 2022-06-11
* Copyright © mxue
*/
@Slf4j
@RestController
@RequestMapping("/wx-ma")
public class WxMaAuthTokenController extends BaseController {

    @Autowired(required=false)
    private WxMaAuthService wxMaAuthService;

    /**
    * 用户登录获取token
    * @param credential
    * @return
    * @throws Exception
    */
    @PostMapping("/auth/login")
    public JsonResult<String> login(@RequestBody MobileCredential credential) throws Exception {
    		credential.setUserTypeClass(IamMember.class);
        String authToken = AuthServiceFactory
                .getAuthService(Cons.DICTCODE_AUTH_TYPE.WX_MP.name())
                .applyToken(credential);
        return JsonResult.OK(authToken);
    }

    /**
     * 微信小程序获取SessionInfo:主要目的获取appid
     *
     * @param code code
     * @return
     */
    @GetMapping("/auth/getSessionInfo")
    public JsonResult getSessionInfo(String code) throws Exception {
        return JsonResult.OK(wxMaAuthService.getSessionInfo(code));
    }

    /**
     * <pre>
     * 保存用户，并创建账号信息
     * </pre>
     */
    @PostMapping("/auth/getAndSaveWxMember")
    public JsonResult getAndSaveWxMember(@RequestBody WxMemberDTO wxInfoDTO) throws Exception {
        return JsonResult.OK(wxMaAuthService.getAndSaveWxMember(wxInfoDTO));
    }
    
    /**
     * <pre>
     * 登陆后绑定授权
     * </pre>
     */
    @PostMapping("/bindMa")
    public JsonResult bindMa(@RequestBody WxMemberDTO wxInfoDTO) throws Exception {
        return JsonResult.OK(wxMaAuthService.bindWxMa(wxInfoDTO));
    }

}