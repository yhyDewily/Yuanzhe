package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Logs;
import com.ruoyi.system.domain.Logs;

import java.util.List;

/**
 * @Logsor Dewily
 * @date 2022-07-13 22:26
 */
public interface LogsMapper {

    public Logs selectLogsById(int logId);

    public List<Logs> selectLogsList(Logs Logs);

    public int insertLogs(Logs logs);

    public int deleteLogsById(int id);

    public int checkEqual(Logs logs);

    public int updateLogs(Logs logs);
}
