package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(value = "员工")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登陆")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("退出")
    public Result<String> logout() {
        return Result.success();
    }


    @PostMapping
    @ApiOperation("新增员工")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long id = employeeDTO.getId();
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("根据页码查询员工")
    public Result<PageResult> getEmployeeByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult pageResult = employeeService.quryForPage(employeePageQueryDTO);
        System.out.println(pageResult);
        return Result.success(pageResult);

    }

    @PostMapping("/status/{status}")
    @ApiOperation("改变员工状态")
    public Result<String> changeStaus(@PathVariable("status") Integer status, Long id) {
        employeeService.changeStaus(status, id);

        return Result.success();

    }

    @GetMapping("/{id}")
    @ApiOperation("根据员工id查询")
    public Result<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);

    }

    @PutMapping
    @ApiOperation("根据员工id查询")
    public Result<Employee> changeEployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.changeEmplyee(employeeDTO);

        return Result.success();

    }
}
