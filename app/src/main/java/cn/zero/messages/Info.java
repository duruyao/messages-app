package cn.zero.messages;

import android.content.Context;
import android.text.format.DateFormat;
import com.dry.messages.Messages;
import com.dry.messages.RegexManager;

import java.util.Date;

public class Info extends Messages {

    public Info(int id, String address, int person, String body, long date, int type, int read, Context context) {
        super(id, address, person, body, date, type, read, context);
    }

}
