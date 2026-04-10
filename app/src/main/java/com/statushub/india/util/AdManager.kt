package com.statushub.india.util

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.FullScreenContentCallback

object AdManager {
    private var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitial(context: Context) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            AppConfig.INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    fun showInterstitial(activity: Activity, onAdDismissed: () -> Unit) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    loadInterstitial(activity) // Preload the next one
                    onAdDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    mInterstitialAd = null
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        } else {
            onAdDismissed()
        }
    }
}
