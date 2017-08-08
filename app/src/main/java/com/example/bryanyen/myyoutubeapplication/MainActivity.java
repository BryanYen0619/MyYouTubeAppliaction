package com.example.bryanyen.myyoutubeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.youtube.com/watch?v=YzevNaeUiHc&list=PLadpa_xY6Oo9CewIqsGQyG0PvxQzKgEBX");
        //        webView.loadDataWithBaseURL(null, "<style>.embed-container { position: relative; padding-bottom:
        // 56.25%; " +
        //                "height: 0; overflow: hidden; max-width: 100%; } .embed-container iframe, .embed-container
        // object, " +
        //                ".embed-container embed { position: absolute; top: 0; left: 0; width: 100%; height: 100%; " +
        //                "}</style><div class='embed-container'><iframe src='https://www.youtube
        // .com/embed/5qOSGyrIgMo' " +
        //                "frameborder='0' allowfullscreen></iframe></div>", "text/html", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean
            wasRestored) {
        if (!wasRestored) {
            //            youTubePlayer.cueVideo("pb-kc6DWIDI");// 凉凉MV https://youtu.be/pb-kc6DWIDI
            youTubePlayer.cuePlaylist("PLadpa_xY6Oo9CewIqsGQyG0PvxQzKgEBX");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
            youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(MainActivity.this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.youtube_error) + youTubeInitializationResult.toString();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
        }
    }
}
