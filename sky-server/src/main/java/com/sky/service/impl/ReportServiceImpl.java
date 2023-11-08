package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/11/3
 * @Description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        List<LocalDate> list = new ArrayList<>();
        list.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            list.add(begin);
        }
        List<Double> turnoverList = new ArrayList<>();

        list.forEach(i -> {
                    LocalDateTime min = LocalDateTime.of(i, LocalTime.MIN);
                    LocalDateTime max = LocalDateTime.of(i, LocalTime.MAX);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("status", Orders.COMPLETED);
                    map.put("begin", min);
                    map.put("end", max);
                    Double t = orderMapper.sumByMap(map);
                    t = t == null ? 0.0 : t;
                    turnoverList.add(t);

                }

        );
        return TurnoverReportVO.builder().dateList(StringUtils.join(list, ","))
                .turnoverList(StringUtils.join(turnoverList, ",")).build();

    }
}
