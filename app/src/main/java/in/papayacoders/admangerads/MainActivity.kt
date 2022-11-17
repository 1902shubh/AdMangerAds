package `in`.papayacoders.admangerads

import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {

    var adManagerInterstitialAd: AdManagerInterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MobileAds.initialize(this) {}

        val banner = findViewById<AdManagerAdView>(R.id.adManagerAdView)
        val adRequest = AdManagerAdRequest.Builder().build()
        banner.loadAd(adRequest)

        loadInterstitialAd()

        loadNativeAds()


        val showInterstitial = findViewById<Button>(R.id.showInterstitialAd)

        showInterstitial.setOnClickListener {
            if (adManagerInterstitialAd != null) {
                adManagerInterstitialAd!!.show(this)
            }
        }


    }

    private fun loadNativeAds() {
        val adLoader: AdLoader = AdLoader.Builder(this, "/6499/example/native")
            .forNativeAd { nativeAd ->
                val styles =
                    NativeTemplateStyle.Builder().build()
                val template = findViewById<TemplateView>(R.id.my_template)
                template.setStyles(styles)
                template.setNativeAd(nativeAd)
                template.visibility = VISIBLE
            }
            .build()

        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }


    private fun loadInterstitialAd() {
        var adRequest = AdManagerAdRequest.Builder().build()

        AdManagerInterstitialAd.load(this, "/6499/example/interstitial", adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("SHUBH", adError.toString())
                    adManagerInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    Log.d("SHUBH", "Ad was loaded.")
                    adManagerInterstitialAd = interstitialAd
                }
            })
    }


}