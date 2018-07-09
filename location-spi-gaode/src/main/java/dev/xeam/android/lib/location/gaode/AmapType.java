package dev.xeam.android.lib.location.gaode;

import dev.xeam.android.lib.location.CLocationType;

/**
 * Created by xesamguo@gmail.com on 17-8-3.
 */

class AmapType implements CLocationType {
    @Override
    public String getType() {
        return "amap";
    }
}
