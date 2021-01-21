/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.revolve44.firstrunsample

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView



class FirstrunPagerAdapter(
    private val context: Context,
    private val listener: View.OnClickListener
) : PagerAdapter() {

    private data class FirstrunPage(val title: String, val rawPassword: String, val emojiPassword: String, val imageResource: Int) {
        val contentDescription = rawPassword + emojiPassword
    }

    private val pages: Array<FirstrunPage>

    init {
        val appName = context.getString(R.string.app_name)
        this.pages = arrayOf(
            FirstrunPage(
                "Ideas for emoji passwords:",
                "",
                "show your \nimagination \n\uD83D\uDE0E\uD83D\uDD25\uD83D\uDCAB✨",
                0),
            FirstrunPage(
                "Your name + birth date",
                "Alex1994",
                "\uD83E\uDDD1\uD83C\uDF82\uD83D\uDDD3️",
                R.drawable.ic_baseline_arrow_downward_24),
            FirstrunPage("Your favorite Pizza restaurant",
                "Bella pizza",
                "\uD83C\uDF55\uD83C\uDFEC",
                R.drawable.ic_baseline_arrow_downward_24),
            FirstrunPage(
                "Your Pet",
                "Jack1234",
                "\uD83D\uDC36\uD83D\uDD22",
                R.drawable.ic_baseline_arrow_downward_24),
            FirstrunPage(
                "Work Phone",
                "+17463372",
                "☎️\uD83D\uDCBC",
                R.drawable.ic_baseline_arrow_downward_24),
            FirstrunPage(
                "Favorite Book",
                "LittlePrince",
                "♥️\uD83D\uDCD6",
                R.drawable.ic_baseline_arrow_downward_24)
        )
    }


    private fun getView(position: Int, pager: ViewPager): View {
        val view = LayoutInflater.from(context).inflate(R.layout.firstrun_page, pager, false)

        val page = pages[position]

        // title
        val titleView: TextView = view.findViewById(R.id.title_view)
        titleView.text = page.title
        // raw
        val rawPasswordView: TextView = view.findViewById(R.id.raw_password)
        rawPasswordView.text = page.rawPassword
        // emoji
        val emojiPasswordView: TextView = view.findViewById(R.id.emoji_password)
        emojiPasswordView.text = page.emojiPassword


        ///////////////////////////////////////////////////////////
        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(page.imageResource)

        val buttonView: Button = view.findViewById(R.id.button)
        buttonView.setOnClickListener(listener)
        ////////////////////////////////////////////////////////////////////
        if (position == pages.size - 1) {
            buttonView.setText("OK")
            buttonView.id = R.id.finish
            buttonView.contentDescription = buttonView.text.toString().toLowerCase()

        } else if (position == 0){

            emojiPasswordView.textSize = 30F
            emojiPasswordView.text = page.emojiPassword
            buttonView.id = R.id.next

        } else {
            buttonView.setText("NEXT")
            buttonView.id = R.id.next
        }

        return view
    }

    fun getPageAccessibilityDescription(position: Int): String =
            pages[position].contentDescription

    override fun isViewFromObject(view: View, any: Any) = view === any

    override fun getCount() = pages.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container as ViewPager

        val view = getView(position, container)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        view as View
        container.removeView(view)
    }
}
