package dev.xesam.android.logtools;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * 简单的日志类
 */
public final class L {
    private final static int TYPE_E = 0;
    private final static int TYPE_W = 1;
    private final static int TYPE_D = 2;
    private final static int TYPE_I = 3;
    private final static int TYPE_V = 4;

    private final static String _NULL = "L[null]";
    private final static String TAG_EMPTY = "L[empty_tag]";
    private final static String CONTENT_EMPTY = "L[empty_content]";
    private final static String CONTENT_DIVIDER = "|";

    private L() {
    }

    private static boolean mEnable = false;

    public static void enable(boolean enable) {
        mEnable = enable;
    }

    public static void e(Object... content) {
        log(TYPE_E, content);

    }

    public static void w(Object... content) {
        log(TYPE_W, content);
    }

    public static void d(Object... content) {
        log(TYPE_D, content);
    }

    public static void i(Object... content) {
        log(TYPE_I, content);
    }

    public static void v(Object... content) {
        log(TYPE_V, content);
    }

    private static void _log(int type, String tag, String contentString) {

        switch (type) {
            case TYPE_E:
                Log.e(tag, contentString);
                break;
            case TYPE_W:
                Log.w(tag, contentString);
                break;
            case TYPE_D:
                Log.d(tag, contentString);
                break;
            case TYPE_I:
                Log.i(tag, contentString);
                break;
            case TYPE_V:
                Log.v(tag, contentString);
                break;
            default:
                break;
        }
    }

    private static void log(int type, Object... content) {
        if (!mEnable) {
            return;
        }
        String tagString;
        String contentString;
        if (content == null) {
            tagString = _NULL;
            contentString = _NULL;
        } else if (content.length == 0) {
            tagString = TAG_EMPTY;
            contentString = CONTENT_EMPTY;
        } else if (content.length == 1) {
            Object tag = content[0];
            tagString = getLogTag(tag);
            contentString = CONTENT_EMPTY;
        } else {
            Object tag = content[0];
            tagString = getLogTag(tag);
            content = Arrays.copyOfRange(content, 1, content.length);
            contentString = genLogContent(content);
        }

        int contentStringLength = contentString.length();
        int limit = 1000;//4096

        if (contentStringLength < limit) {
            _log(type, tagString, contentString);
        } else {
            int round = (int) Math.ceil(contentStringLength * 1f / limit);
            for (int i = 0; i < round; i++) {
                _log(type, tagString, contentString.substring(limit * i, limit * (i + 1) < contentStringLength ? limit * (i + 1) : contentStringLength));
            }
        }
    }

    private static String getTagFromView(View view) {
        return view.getTag() != null
                ? view.getTag().toString()
                : TextUtils.isEmpty(view.getContentDescription()) ? view.getClass().getSimpleName() : view.getContentDescription().toString();
    }

    private static String getLogTag(Object object) {
        if (object == null) {
            return _NULL;
        } else if (object instanceof Number) {
            return object.toString();
        } else if (object instanceof String) {
            return object.toString();
        } else if (object instanceof View) {
            return getTagFromView((View) object);
        } else {
            if (object.getClass().isAnonymousClass()) {
                String[] segs = object.getClass().getName().split("\\.");
                if (segs.length == 1) {
                    return segs[0];
                } else {
                    return segs[segs.length - 1];
                }
            } else {
                return object.getClass().getSimpleName();
            }
        }
    }

    private static String genLogContent(Object... content) {
        if (null == content) {
            return _NULL;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = content.length; i < size; i++) {
            Object c = content[i];
            sb.append(null == c ? _NULL : c.toString());
            if (i != size - 1) {
                sb.append(CONTENT_DIVIDER);
            }
        }
        return sb.toString();
    }
}
