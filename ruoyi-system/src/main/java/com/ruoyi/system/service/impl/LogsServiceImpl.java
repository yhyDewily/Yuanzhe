package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Logs;
import com.ruoyi.system.mapper.LogsMapper;
import com.ruoyi.system.service.ILogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:32
 */
@Service
public class LogsServiceImpl implements ILogsService {

    @Autowired
    private LogsMapper logsMapper;

    @Override
    public Logs selectLogsById(int logId) {
        return logsMapper.selectLogsById(logId);
    }

    @Override
    public List<Logs> selectLogsList(Logs logs) {
        return logsMapper.selectLogsList(logs);
    }

    @Override
    public int insertLogs(Logs logs) {
        if (!checkExist(logs)) return 0;
        return logsMapper.insertLogs(logs);
    }

    @Override
    public int deleteLogsById(int logId) {
        return logsMapper.deleteLogsById(logId);
    }

    @Override
    public boolean checkExist(Logs logs) {
        return logsMapper.checkEqual(logs) == 0;
    }

    @Override
    public int updateLogs(Logs logs) {
        if (this.checkExist(logs)) return 0;
        return logsMapper.updateLogs(logs);
    }
}
