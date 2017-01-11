package app.application.custom;


import android.content.Context;
import android.support.annotation.ColorInt;

import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;

import static com.beardedhen.androidbootstrap.utils.ColorUtils.ACTIVE_OPACITY_FACTOR_EDGE;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.ACTIVE_OPACITY_FACTOR_FILL;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.DISABLED_ALPHA_EDGE;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.DISABLED_ALPHA_FILL;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.decreaseRgbChannels;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.increaseOpacity;
import static com.beardedhen.androidbootstrap.utils.ColorUtils.resolveColor;

public class CustomBootstrapColors implements BootstrapBrand {

    int color;
    int textColor;

    public CustomBootstrapColors(int color){
        this.color = color;
        this.textColor =  android.R.color.white;
    }

    CustomBootstrapColors(int color, int textColor){
        this.color = color;
        this.textColor =  textColor;
    }

    @ColorInt
    public int defaultFill(Context context) {
        return resolveColor(color, context);
    }

    @ColorInt public int defaultEdge(Context context) {
        return decreaseRgbChannels(context, color, ACTIVE_OPACITY_FACTOR_EDGE);
    }

    @ColorInt public int activeFill(Context context) {
        return decreaseRgbChannels(context, color, ACTIVE_OPACITY_FACTOR_FILL);
    }

    @ColorInt public int activeEdge(Context context) {
        return decreaseRgbChannels(context, color, ACTIVE_OPACITY_FACTOR_FILL + ACTIVE_OPACITY_FACTOR_EDGE);
    }

    @ColorInt public int disabledFill(Context context) {
        return increaseOpacity(context, color, DISABLED_ALPHA_FILL);
    }

    @ColorInt public int disabledEdge(Context context) {
        return increaseOpacity(context, color, DISABLED_ALPHA_FILL - DISABLED_ALPHA_EDGE);
    }

    @ColorInt public int defaultTextColor(Context context) {
        return resolveColor(textColor, context);
    }

    @ColorInt public int activeTextColor(Context context) {
        return resolveColor(textColor, context);
    }

    @ColorInt public int disabledTextColor(Context context) {
        return resolveColor(textColor, context);
    }

    @ColorInt public int getColor() {
        return color;
    }
}
