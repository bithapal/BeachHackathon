package fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hacks.beachapp.beachapp.R;
import hacks.beachapp.TermsAndConditions;

public class FragmentLogin extends Fragment {
    View view;
    TextView tvTermsPolicy;
    Context context;
    //spannable string
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_login, container, false);
        tvTermsPolicy = (TextView) view.findViewById(R.id.tvTermsPolicy);
        context = getContext();
        String terms = "Terms and Conditions";
        String policy = "Privacy Policy";
        String kitchen = "<font color=#000000>By logging in you agree to our:</font> <font color=#f89f00>" + terms +
                "</font> <font color=#000000>and</font> <font color=#f89f00>" + policy;
        tvTermsPolicy.setText(Html.fromHtml(kitchen));

        SpannableString ss = new SpannableString(Html.fromHtml(kitchen));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent i=new Intent(context, TermsAndConditions.class);
                context.startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.yellow));
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent i=new Intent(context, TermsAndConditions.class);
                context.startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
               ds.setColor(getResources().getColor(R.color.yellow));
            }
        };
        ss.setSpan(clickableSpan, 32, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan1, 57, 71, Spanned.SPAN_INTERMEDIATE);
        tvTermsPolicy.setText(ss);
        tvTermsPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        tvTermsPolicy.setHighlightColor(Color.TRANSPARENT);
        return view;
    }
}
