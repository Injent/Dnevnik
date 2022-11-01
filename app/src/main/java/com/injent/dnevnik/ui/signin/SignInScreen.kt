package com.injent.dnevnik.ui.signin

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.injent.dnevnik.NavActions
import com.injent.dnevnik.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navActions: NavActions,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.sign_in))
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            loadWebUrl(
                url = stringResource(id = R.string.token_request_url),
                onTokenReceived = {
                    viewModel.auth(it) {
                        navActions.navigateToWorkScreen()
                    }
                }
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun loadWebUrl(
    modifier: Modifier = Modifier.fillMaxSize(),
    url: String,
    onTokenReceived: (String) -> Unit
) {
    AndroidView(modifier = modifier, factory = {
        WebView(it).apply {
            webViewClient = CustomWebViewClient(onTokenReceived)
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}

private class CustomWebViewClient(
    private val listener: (String) -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val url = request!!.url.toString();
        val param = "#access_token="
        if (url.contains(param)) {
            val token = url
                .substring(url.indexOf(param), url.indexOf("&state="))
                .substring(param.length)
            listener(token)
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}

@Composable
fun StuEdu() {

}