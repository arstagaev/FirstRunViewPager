package com.revolve44.firstrunsample

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class FirstrunFragment : Fragment(), View.OnClickListener {

    private var viewPager: ViewPager? = null

    private var background: View? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onAttach(context: Context) {
        super.onAttach(context)

        //val transition = TransitionInflater.from(context).inflateTransition(R.transition.firstrun_exit)
//
//        exitTransition = transition

        // We will send a telemetry event whenever a new firstrun page is shown. However this page
        // listener won't fire for the initial page we are showing. So we are going to firing here.
        //TelemetryWrapper.showFirstRunPageEvent(0)
    }

    @Suppress("MagicNumber")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first_run, container, false)

        view.findViewById<View>(R.id.skip).setOnClickListener(this)

        background = view.findViewById(R.id.background)

        val adapter = FirstrunPagerAdapter(container!!.context, this)

        viewPager = view.findViewById(R.id.pager)
        viewPager!!.contentDescription = adapter.getPageAccessibilityDescription(0)
        viewPager!!.isFocusable = true

        viewPager!!.setPageTransformer(true) { page, position -> page.alpha = 1 - 0.5f * Math.abs(position) }

        viewPager!!.clipToPadding = false
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                //TelemetryWrapper.showFirstRunPageEvent(position)
                // may use TransitionDrawable for smooth transition of background
                val drawable = background!!.background as TransitionDrawable

                if (position == adapter.count - 1) {
                    drawable.startTransition(200)
                   // drawable.color = Color.BLACK
                } else {
                   drawable.resetTransition()
                   // drawable.color = Color.MAGENTA
                }

                viewPager!!.contentDescription = adapter.getPageAccessibilityDescription(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager, true)

        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.next -> viewPager!!.currentItem = viewPager!!.currentItem + 1

            R.id.skip -> {
                finishFirstrun()
                //TelemetryWrapper.skipFirstRunEvent()
            }

            R.id.finish -> {
                finishFirstrun()
                //TelemetryWrapper.finishFirstRunEvent()
            }

            else -> throw IllegalArgumentException("Unknown view")
        }
    }

    private fun finishFirstrun() {
        val fragmentManager = requireActivity().supportFragmentManager

//        PreferenceManager.getDefaultSharedPreferences(requireContext())
//            .edit()
//            .putBoolean(FIRSTRUN_PREF, true)
//            .apply()

//        val sessionUUID = arguments!!.getString(ARGUMENT_SESSION_UUID)
//        val sessionManager = requireContext().components.sessionManager

//        val fragment: Fragment
//        fragment = if (sessionUUID == null) {
//            UrlInputFragment.createWithoutSession()
//        } else {
//            val session = sessionManager.findSessionById(sessionUUID)
//            BrowserFragment.createForSession(session!!)
//        }
//
//        val fragmentTag =
//            if (fragment is BrowserFragment) BrowserFragment.FRAGMENT_TAG else UrlInputFragment.FRAGMENT_TAG



//        fragmentManager
//            .beginTransaction()
//            .replace(R.id.container, fragment, fragmentTag)
//            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onResume() {
        super.onResume()
        StatusBarUtils.getStatusBarHeight(background) { statusBarHeight ->
            background!!.setPadding(
                0,
                statusBarHeight,
                0,
                0
            )
        }
    }

    companion object {
        const val FRAGMENT_TAG = "firstrun"
        const val FIRSTRUN_PREF = "firstrun_shown"

        private const val ARGUMENT_SESSION_UUID = "sessionUUID"

//        fun create(currentSession: Session?): FirstrunFragment {
//            val uuid = currentSession?.id
//
//            val arguments = Bundle()
//            arguments.putString(ARGUMENT_SESSION_UUID, uuid)
//
//            val fragment = FirstrunFragment()
//            fragment.arguments = arguments
//
//            return fragment
//        }
    }
}