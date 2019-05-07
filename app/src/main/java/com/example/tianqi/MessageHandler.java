package com.example.tianqi;

import android.os.Handler;
import android.os.Message;

// LoopView
// 日期选择器组件

final class MessageHandler extends Handler {

    final LoopView a;

    MessageHandler(LoopView loopview) {
        super();
        a = loopview;
    }

    public final void handleMessage(Message paramMessage) {
        if (paramMessage.what == 1000)
            this.a.invalidate();
        while (true) {
            if (paramMessage.what == 2000)
                LoopView.b(a);
            else if (paramMessage.what == 3000)
                this.a.c();
            super.handleMessage(paramMessage);
            return;
        }
    }

}
