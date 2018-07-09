package dev.xeam.android.lib.location.baidu;

import dev.xeam.android.lib.location.CLocationType;

/**
 * Created by xesamguo@gmail.com on 17-8-3.
 */

class BaiduType implements CLocationType {
    @Override
    public String getType() {
        return "baidu";
    }
}
