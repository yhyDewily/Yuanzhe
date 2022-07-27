package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.Logs;
import com.ruoyi.system.service.ILogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:27
 */
@Controller
@RequestMapping("/system/logs")
public class LogsController extends BaseController {

    @Autowired
    private ILogsService logsService;

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        Logs logs = logsService.selectLogsById(id);
        if (logs != null) return AjaxResult.success(logs);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getLogsList(Logs logs) {
        List<Logs> list = logsService.selectLogsList(logs);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(@RequestBody Logs logs) {
        int add_flag = logsService.insertLogs(logs);
        if (add_flag == 0) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody Logs logs) {
        int edit_flag = logsService.updateLogs(logs);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(logsService.deleteLogsById(id));
    }
}
