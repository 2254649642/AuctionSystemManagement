package com.ws.base.controller.home;


import com.ws.base.service.admin.BiddingProjectService;
import com.ws.base.service.admin.LabelTypeService;
import com.ws.base.bean.PageBean;
import com.ws.base.bean.UserStatus;
import com.ws.base.entity.common.BiddingProject;
import com.ws.base.entity.home.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 竞价大厅
 */
@Controller
@RequestMapping("/home/agency")
public class HomeBiddingAgencyController {

    @Autowired
    private BiddingProjectService biddingProjectService;

    @Autowired
    private LabelTypeService labelTypeService;

    /**
     * 竞价大厅
     * @param model
     * @param pageBean
     * @param projectVo
     * @return
     */
    @GetMapping("/list")
    public String list(Model model, PageBean<BiddingProject> pageBean, ProjectVo projectVo)
    {
        pageBean.setPageSize(12);

        model.addAttribute("labelTypes", labelTypeService.findByStatus(UserStatus.ACTIVE.getCode()));
        model.addAttribute("pageBean", biddingProjectService.findListByAgency(pageBean, projectVo));
        model.addAttribute("projectStatus", projectVo.getProjectStatus());
        model.addAttribute("province", projectVo.getProvince());
        model.addAttribute("city", projectVo.getCity());
        model.addAttribute("area", projectVo.getArea());
        model.addAttribute("labelType", projectVo.getLabelType());
        return "home/agency/list";
    }
}
