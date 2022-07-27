package com.ruoyi.system.service;

import com.ruoyi.system.domain.Logs;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface ILogsService {

    public Logs selectLogsById(int logId);

    public List<Logs> selectLogsList(Logs logs);

    public int insertLogs(Logs logs);

    public int deleteLogsById(int logId);

    public boolean checkExist(Logs logs);

    public int updateLogs(Logs logs);
}
