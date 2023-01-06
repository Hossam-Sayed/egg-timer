/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.eggtimernotifications.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.databinding.FragmentEggTimerBinding
import com.google.firebase.messaging.FirebaseMessaging

class EggTimerFragment : Fragment() {

    private val TOPIC = "breakfast"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEggTimerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_egg_timer, container, false
        )

        val viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        // TODO: Step 1.7 call create channel

//        You must specify the right notification id as the one you provided for the builder
//        object in NotificationUtils.kt class, otherwise the notification will fail
        createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

        createChannel(
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name)
        )

        subscribeTopic()
        return binding.root
    }

    /**
     * Creates the notification channel
     *
     * @param channelId uniquely identifies each channel
     * @param channelName represents the name of the channel
     * */
    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
//        Check if the API level is 26 or higher (Oreo or higher)
//        Because notification channels only work for API level 26 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Create the channel object
            /**
             * Creates the channel object by instantiating [NotificationChannel] class
             * which takes [String] [channelId], [String] [channelName], and [Int] importance
             * */
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                    .apply { setShowBadge(false) }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time or breakfast"

//            Creates object from NotificationManager and pass the channel as argument for it
//            That ends creating a channel. You just need to call it in the right place, for example onCreateView()
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // TODO: Step 1.6 END create a channel

    }

    private fun subscribeTopic() {
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var msg = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]
    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}

