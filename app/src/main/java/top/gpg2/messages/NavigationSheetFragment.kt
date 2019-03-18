package top.gpg2.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_navigation_sheet.*

// Reference: https://medium.com/halcyon-mobile/implementing-googles-refreshed-modal-bottom-sheet-4e76cb5de65b

class NavigationSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Bottom Navigation Drawer Sheet Dialog Menu Item
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) { // Switch UI between conversation types
            //    R.id.nav_inbox ->
            //    R.id.nav_notice ->
            //    R.id.nav_ticket ->
            //    R.id.nav_pin ->
            }
            true
        }
    }

    // Rounded Corner Theme
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
}
