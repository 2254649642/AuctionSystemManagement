package com.ws.base.controller.home;

import com.alipay.api.internal.util.StringUtils;
import com.ws.base.bean.CodeMsg;
import com.ws.base.bean.PayUserType;
import com.ws.base.bean.Result;
import com.ws.base.entity.home.HomeUser;
import com.ws.base.entity.home.UserAlipay;
import com.ws.base.service.home.HomeUserService;
import com.ws.base.service.home.UserAlipayService;
import com.ws.base.util.AlipayUtil;
import com.ws.base.util.SessionUtil;
import com.ws.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/home/user_alipay")
public class UserAlipayController {

    @Value("${ws.alipay.title}")
    private String subject;

    @Value("${ws.alipay.body}")
    private String body;

    @Autowired
    private UserAlipayService userAlipayService;
    
    @Autowired
    private HomeUserService homeUserService;

    /**
     * 生成订单
     */
    @PostMapping("/create_order")
    @ResponseBody
    public Result<UserAlipay> createOrder(UserAlipay userAlipay){
        HomeUser homeUser = SessionUtil.getLoginedHomeUser();
        if(homeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        if(userAlipay == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if(userAlipay.getTotalAmount() == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if(userAlipay.getTotalAmount().compareTo(new BigDecimal(1))<0){
            return Result.error(CodeMsg.ADMIN_ORG_ALIPAY_NUMBER_ADD_ERROR);
        }
        if(userAlipay.getTotalAmount().compareTo(UserAlipay.max_money) > 0){
            return Result.error(CodeMsg.ALIPAY_NUMBER_MORE_ERROR);
        }

        userAlipay.setOutTradeNo(StringUtil.gneerateSn("user",""));
        userAlipay.setSubject(subject);
        userAlipay.setBody(body);
        userAlipay.setHomeUser(homeUser);

        if(userAlipayService.save(userAlipay) == null){
            return Result.error(CodeMsg.ADMIN_ORG_ALIPAY_ADD_ERROR);
        }

        return Result.success(userAlipay);
    }

    /**
     * 支付
     * @param outTradeNo
     * @param model
     * @return
     */
    @GetMapping("/to_pay")
    public String toPay(String outTradeNo, Model model) throws Exception {
        UserAlipay userAlipay = userAlipayService.findByOutTradeNo(outTradeNo);
        if(userAlipay.getStatus() == UserAlipay.pay_status_pid){
            model.addAttribute("html","你已经支付过了");
            return "home/home_user/alipay_pe";
        }

        String html = AlipayUtil.genHtml(userAlipay.getOutTradeNo(), userAlipay.getTotalAmount().toString(), userAlipay.getSubject(), userAlipay.getBody(), PayUserType.HOMEUSER.getCode());
        model.addAttribute("html",html);

        return "home/home_user/alipay_pe";
    }

    @RequestMapping("/alipay_notify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request){

        //验证
        if(!AlipayUtil.isValid(request)){
            return "fail";
        }

        String outTradeNo = request.getParameter("out_trade_no");//订单号
        String paySn = request.getParameter("trade_no");//交易号
        String totalAmount = request.getParameter("total_amount");//金额
        String status = request.getParameter("trade_status");//支付状态

        if(StringUtils.isEmpty(outTradeNo) || StringUtils.isEmpty(paySn) || StringUtils.isEmpty(totalAmount) || StringUtils.isEmpty(status))
        {
            return "fail";
        }

        UserAlipay userAlipay = userAlipayService.findByOutTradeNo(outTradeNo);
        if(userAlipay == null){
            return "fail";
        }
        if(userAlipay.getTotalAmount().compareTo(new BigDecimal(totalAmount)) != 0){
            return "fail";
        }

        userAlipay.setPaySn(paySn);
        userAlipay.setPayTime(new Date());
        userAlipay.setStatus(UserAlipay.pay_status_pid);
        userAlipayService.save(userAlipay);

        HomeUser alipayHomeUser = userAlipay.getHomeUser();
        HomeUser homeUser = homeUserService.find(alipayHomeUser.getId());
        homeUser.setBalance(homeUser.getBalance().add(userAlipay.getTotalAmount()));
        homeUserService.save(homeUser);
        return "success";
    }
}
