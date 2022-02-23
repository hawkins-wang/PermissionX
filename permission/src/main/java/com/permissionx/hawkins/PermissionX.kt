package com.permissionx.hawkins

import androidx.fragment.app.FragmentActivity

/**
 * 单例工具类
 */
object PermissionX {

    private const val TAG = "InvisibleFragment"

    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existFragment != null) {
            existFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }
}