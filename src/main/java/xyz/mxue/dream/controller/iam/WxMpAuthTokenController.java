package xyz.mxue.dream.controller.iam;

import com.diboot.core.controller.BaseController;
import com.diboot.core.vo.JsonResult;
import com.diboot.mobile.service.WxMpAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
* 微信公众号登陆接口
* @author mxuexxmy
* @version 1.0
* @date 2022-06-11
* Copyright © mxue
*/
@Slf4j
@RestController
@RequestMapping("/wx-mp")
public class WxMpAuthTokenController extends BaseController {
    @Autowired(required=false)
    private WxMpAuthService wxMpAuthService;
    
    /**
     * 通过公众号获取认证URL：获取前端微信重定向的认证地址
     * @param url 前端提交的URL，用于前端重定向
     * @return
     * @throws Exception
     */
    @GetMapping("/auth/buildOAuthUrl")
    public JsonResult buildOAuthUrl4mp(@RequestParam(value = "url") String url) throws Exception {
        return JsonResult.OK(wxMpAuthService.buildOAuthUrl4mp(url));
    }

    /**
     * 通过code登陆，并获取用户信息
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @GetMapping("/auth/apply")
    public JsonResult apply(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state) throws Exception {
        return JsonResult.OK(wxMpAuthService.applyToken(code, state));
    }
    
     /**
     * 登陆后绑定授权
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @GetMapping("/bindMp")
    public JsonResult bindMp(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state) throws Exception {
        return JsonResult.OK(wxMpAuthService.bindWxMp(code, state));
    }

}