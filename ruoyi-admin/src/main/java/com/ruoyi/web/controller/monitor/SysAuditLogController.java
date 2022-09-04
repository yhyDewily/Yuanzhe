package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.vo.SysLogVO;
import com.ruoyi.system.service.ISysOperLogAuditService;
import com.ruoyi.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/audit/log")
public class SysAuditLogController extends BaseController {

    @Autowired
    private ISysOperLogService operLogService;

    @Autowired
    private ISysOperLogAuditService operLogAuditService;

    @PreAuthorize("@ss.hasRole('audit_operator')")
    @PostMapping("/audit_log")
    public AjaxResult auditLog(@RequestBody SysLogVO sysLogVO) {
        if (operLogAuditService.auditLog(sysLogVO) == 0) return AjaxResult.success();
        else return AjaxResult.error("审计失败");
    }

    @PreAuthorize("@ss.hasAnyRoles('audit_operator,super_admin')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogVO logVO) {
        startPage();
        List<SysLogVO> sysLogVOS = operLogService.selectOperLogList(logVO);
        return getDataTable(sysLogVOS);
    }

}
