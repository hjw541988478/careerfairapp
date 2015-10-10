package in.harvestday.careerfairs.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.harvestday.careerfairs.R;


public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.id_webview)
    WebView webView;

    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    String relativeUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        relativeUrl = getIntent().getStringExtra("url");
        webView.setWebViewClient(new AgreementWebViewClient());
        webView.loadUrl("http://m.haitou.cc/xjh-" + relativeUrl.substring(9, 15));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
    }

    private class AgreementWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
