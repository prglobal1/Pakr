package com.webviewapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    // ========================================================
    // FIX: Forzar el idioma español antes de crear la Activity.
    // Esto evita que el launcher y la interfaz nativa se
    // muestren en chino (idioma por defecto del proyecto).
    // ========================================================
    override fun attachBaseContext(newBase: Context) {
        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.mediaPlaybackRequiresUserGesture = false

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        // IMPORTANTE: NO borres "{{APP_URL}}", el workflow
        // de GitHub Actions lo reemplaza automáticamente
        // con la URL que escribas en el formulario.
        webView.loadUrl("{{APP_URL}}")
    }

    // Permite que el botón "atrás" del móvil navegue por el
    // historial de la web en lugar de cerrar la app.
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
