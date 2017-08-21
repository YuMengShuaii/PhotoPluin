package com.enation.javashop.photoutils.permission;


import com.enation.javashop.photoutils.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
