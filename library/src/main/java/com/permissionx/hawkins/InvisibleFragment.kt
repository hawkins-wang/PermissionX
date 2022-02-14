package com.permissionx.hawkins

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

//给后面的类型指定别名
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    private var callBack: PermissionCallback? = null
    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callBack = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            //存放被拒绝的权限
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            //不为空则调用callback的回调方法
            callBack?.let {
                it(allGranted, deniedList)
            }

        }
    }
}