package indi.toaok.animation.core.property.widget.coustom;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

/**
 * @author Toaok
 * @version 1.0  2019/7/25.
 */
public class PrintWordLayout extends StaticLayout {

    public PrintWordLayout(CharSequence source, TextPaint paint, int width, Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        super(source, paint, width, align, spacingmult, spacingadd, includepad);
    }

    public PrintWordLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Alignment align, float spacingmult, float spacingadd, boolean includepad) {
        super(source, bufstart, bufend, paint, outerwidth, align, spacingmult, spacingadd, includepad);
    }

    public PrintWordLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth) {
        super(source, bufstart, bufend, paint, outerwidth, align, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth);
    }


    @Override
    public void draw(Canvas canvas, Path highlight, Paint highlightPaint, int cursorOffsetVertical) {

        super.draw(canvas, highlight, highlightPaint, cursorOffsetVertical);

    }
}
