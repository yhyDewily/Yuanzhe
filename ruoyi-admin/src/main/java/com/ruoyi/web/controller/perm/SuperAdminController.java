package com.ruoyi.web.controller.perm;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author chenshijie
 * @date 2022/8/29 16:42
 */

@RestController
@RequestMapping("/superadmin")

public class SuperAdminController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    private SysLoginService sysLoginService;





    /**
     * 新增业务管理员
     * @author csj
     */
    @PreAuthorize("{@ss.hasPermi('perm:operator:list') and @ss.hasRole('super_admin')}")
//    @PreAuthorize("@ss.hasRole('admin')")
    @Log(title = "操作员管理", businessType = BusinessType.INSERT)
    @PostMapping("/addBusinessAdmin")
    public AjaxResult addBusinessAdmin(@Validated @RequestBody SysUser user)
    {
        String username = getUsername();
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setNickName("业务管理员");
        if (StringUtils.isNotEmpty(user.getSex())) {
            if (user.getSex().equals("男")||user.getSex().equals("1")) {
                user.setSex("1");
            }
            else if (user.getSex().equals("女")||user.getSex().equals("0")) {
                user.setSex("0");
            }
            else {
                user.setSex("2");
            }
        }

        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        Long ids = 2l;
        int suc = userService.insertUser(user);
        System.out.println("__________________"+user.getUserId()+"__________________");
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(user.getUserId());
        sysUserRole.setRoleId(2l);
        sysUserRoleMapper.insertUserRole(sysUserRole);
        return toAjax(suc);
    }

    /**
     * 删除/注销用户(仅可删除roleId为2)
     */
    @PreAuthorize("{@ss.hasPermi('perm:operator:list') and @ss.hasRole('super_admin')}")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{userId}")
    public AjaxResult remove(@PathVariable Long userId)
    {

//        if (ArrayUtils.contains(userId, getUserId()))
//        {
//            return error("当前用户不能删除");
//        }

        SysUser sysUser = userService.selectUserById(userId);
        if (!sysUser.getNickName().equals("业务管理员")) {
            return AjaxResult.error().put("msg", "无权删除该用户");
        }
        userService.deleteUserById(userId);
        sysUser.setStatus("1");
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(2l);
        sysUserRole.setUserId(userId);
        userService.updateUser(sysUser);
        sysUserRoleMapper.insertUserRole(sysUserRole);
//        userService
        return AjaxResult.success().put("msg","注销成功");
    }

    /**
     * 获取用户列表（查询role为2的列表）
     */

    @PreAuthorize("{@ss.hasPermi('perm:operator:list') and @ss.hasRole('super_admin')}")
    @GetMapping("/list")
    public TableDataInfo list()
    {

        startPage();
        List<Integer> list = sysUserRoleMapper.selectUserIdByRoleId((long) 2);
        List<SysUser> sysUsers = new ArrayList<>();
        if (list == null) return getDataTable(null);
        for (Integer i : list) {
            SysUser sysUser = userService.selectUserById(Long.valueOf(i));
//            if (sysUser.getDelFlag().equals("0")) {
//                sysUsers.add(sysUser);
//            }else{
//                String msg = "该账号已被删除";
//                System.out.println(msg);
//            }
            sysUsers.add(sysUser);
        }

        return getDataTable(sysUsers);
    }

    /**
     * 根据用户编号获取详细信息
     *
     */

    @GetMapping(value = {"/{userId}" })
    @PreAuthorize("{@ss.hasPermi('perm:operator:list') and @ss.hasRole('super_admin')}")
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 修改用户
     *
     * 根据id和用户名修改
     */

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @PreAuthorize("{@ss.hasPermi('perm:operator:list') and @ss.hasRole('super_admin')}")
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        if (user.getUserId()==null) {
            return AjaxResult.error().put("msg","请输入要修改的用户id");
        }
        SysUser sysUser = userService.selectUserById(user.getUserId());
        if (!sysUser.getNickName().equals("业务管理员")) {
            return AjaxResult.error().put("msg","该用户你无权修改");
        }

        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        if (StringUtils.isNotEmpty(user.getPassword())) {
            return AjaxResult.error("修改用户"+user.getUserName() + "禁止修改密码");
        }

        if (user.getSex().equals("男")||user.getSex().equals("1")) {
            user.setSex("1");
        }
        else if (user.getSex().equals("女")||user.getSex().equals("0")) {
            user.setSex("0");
        }
        else {
            user.setSex("2");
        }

        System.out.println("----------------------");
        String username = getUsername();
        user.setUpdateBy(getUsername());
        userService.updateUser(user);

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(user.getUserId());
        sysUserRole.setRoleId(2l);
        sysUserRoleMapper.insertUserRole(sysUserRole);

        System.out.println("------------");
        return AjaxResult.success().put("msg","修改成功");
    }

    /**
     * 修改密码
     * 仅可修改自己
     */
    @PreAuthorize("{@ss.hasPermi('perm:password:update') and @ss.hasRole('super_admin')}")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody Map<String,String> params) {
        String userId = params.get("userId");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String confirm = params.get("confirm");
        if (userId==null||oldPassword==null||newPassword==null||confirm==null) {
            return AjaxResult.error().put("msg","请输入所有信息");
        }
        Long firstUserId = getUserId();
        Long Id = Long.valueOf(userId);
        if (firstUserId!=Id) {
            return AjaxResult.error().put("msg","请输入正确的用户id");
        }
        String password = getLoginUser().getUser().getPassword();


//        Boolean f = sysLoginService.confirm(username, oldPassword);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean f = encoder.matches(oldPassword, password);

        if (!f) {
            return AjaxResult.error().put("msg","密码不正确");
        }


        if (!newPassword.equals(confirm)) {
            return AjaxResult.error().put("msg","两次输入的新密码不相同");
        }
        newPassword = SecurityUtils.encryptPassword(newPassword);

        SysUser sysUser = new SysUser();
        sysUser.setUserId(getUserId());
        sysUser.setUserName(getUsername());


        sysUser.setPassword(newPassword);
        userService.updateUser(sysUser);

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(getUserId());
        sysUserRole.setRoleId(6l);
        sysUserRoleMapper.insertUserRole(sysUserRole);



//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        user.setUpdateBy(getUsername());
        return AjaxResult.success().put("msg","密码修改成功");
    }

    @PostMapping("test")
    public AjaxResult test2(@RequestBody SysUserRole sysUserRole) {

        return toAjax(sysUserRoleMapper.insertUserRole(sysUserRole));
    }




}
