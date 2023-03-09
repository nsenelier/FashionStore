/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cyberwalker.fashionstore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.cyberwalker.fashionstore.navigation.FashionNavGraph
import com.cyberwalker.fashionstore.ui.theme.FashionStoreTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth

    val TAG = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FashionStoreTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                    TutorialCrashlyticsReport()
                }
            }
        }
        //Firebase
        Firebase.initialize(this) // precaution to ensure Firebase is always initialized
        analytics = Firebase.analytics
        auth = Firebase.auth

        analytics.logEvent(
            FirebaseAnalytics.Event.SELECT_CONTENT,
            bundleOf(
                FirebaseAnalytics.Param.ITEM_ID to "MainActivity",
                FirebaseAnalytics.Param.ITEM_NAME to "UserLoggedIN",
                FirebaseAnalytics.Param.CONTENT_TYPE to "text"
            )
        )
        FirebaseMessaging.getInstance().token.addOnCompleteListener(this) {
            if (!it.isSuccessful) {
                //Could not get FirebaseMessagingToken
                Log.d(TAG, "No Token")
            }
            if (null != it.result) {
                //Got FirebaseMessagingToken
                val firebaseMessagingToken: String = it.result
                Log.d(TAG, "Token: $firebaseMessagingToken")
                //Use firebaseMessagingToken further
            }
        }

    }


}

@Composable
fun App() {
    FashionNavGraph()
}

@Composable
fun TutorialCrashlyticsReport(){
      Button(onClick = { throw RuntimeException("Test Crash") // Force a crash
       },
      modifier= Modifier.requiredSize(26.dp)
      ) {
        Text(text = "Test Crash")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FashionStoreTheme {
        TutorialCrashlyticsReport()
        App()
    }
}