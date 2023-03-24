package com.jamesmumo.tubonge.adapter.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.jamesmumo.tubonge.R;

public class Policy extends AppCompatActivity {

    WebView webView;
    ImageView imageView3;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        webView = findViewById(R.id.webView);
        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(v -> onBackPressed());
        webView.requestFocus();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setSoundEffectsEnabled(true);
        webView.loadData("<!DOCTYPE html>\n" +
                        "    <html>\n" +
                        "    <head>\n" +
                        "      <meta charset='utf-8'>\n" +
                        "      <meta name='viewport' content='width=device-width'>\n" +
                        "      <title>Privacy Policy</title>\n" +
                        "      <style> body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; padding:1em; } </style>\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "    <strong>Privacy Policy</strong> <p>\n" +
                        "                  The App is built by James Mumo as\n" +
                        "                  a Free app. This SERVICE is provided by\n" +
                        "                  \"Tubonge\" at no cost and is intended for use as\n" +
                        "                  is.\n" +

                        "                </p> <p><strong>Security</strong></p> <p>\n" +
                        "                  We value your trust in providing us your\n" +
                        "                  Personal Information, thus we are striving to use commercially\n" +
                        "                  acceptable means of protecting it. But remember that no method\n" +
                        "                  of transmission over the internet, or method of electronic\n" +
                        "                  storage is 100% secure and reliable, and we cannot\n" +
                        "                  guarantee its absolute security.\n" +

                        "                </p> <p><strong>Children’s Privacy</strong></p> <p>\n" +
                        "                  These Services do not address anyone under the age of 13.\n" +
                        "                  We do not knowingly collect personally\n" +
                        "                  identifiable information from children under 13. In the case\n" +
                        "                  we discover that a child under 13 has provided\n" +
                        "                  us with personal information, we immediately\n" +
                        "                  delete this from our servers. If you are a parent or guardian\n" +
                        "                  and you are aware that your child has provided us with\n" +
                        "                  personal information, please contact us so that\n" +
                        "                  we will be able to do necessary actions.\n" +

                        "                </p> <p><strong>Changes to This Privacy Policy</strong></p> <p>\n" +
                        "                  We may update our Privacy Policy from\n" +
                        "                  time to time. Thus, you are advised to review this page\n" +
                        "                  periodically for any changes. We will\n" +
                        "                  notify you of any changes by posting the new Privacy Policy on\n" +
                        "                  this page.\n" +

                        "                </p> <p>This policy is effective as of 2023-01-01</p> <p><strong>Contact Us</strong></p> <p>\n" +
                        "                  If you have any questions or suggestions about our\n" +
                        "                  Privacy Policy, do not hesitate to contact the developer \n" +
                        "                  at mumojames98@gmail.com\n" +
                        "                </p> <p>\n" +
                        "    </body>\n" +
                        "    </html>\n" +
                        "      ",
                "text/html", "UTF-8");
    }
}