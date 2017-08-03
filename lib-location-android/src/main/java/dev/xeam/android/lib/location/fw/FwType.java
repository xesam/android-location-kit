package dev.xeam.android.lib.location.fw;

import dev.xeam.android.lib.location.CLocationType;

/**
 * Created by xesamguo@gmail.com on 17-8-3.
 */

class FwType implements CLocationType {
    @Override
    public String getType() {
        return "android";
    }
}
