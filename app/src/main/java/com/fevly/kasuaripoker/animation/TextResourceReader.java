
package com.fevly.kasuaripoker.animation;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {

    public static String readTextFileFromResource(Context context,
        int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources()
                .openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream);
            BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
        } catch (Resources.NotFoundException nfe) {

        }

        return body.toString();
    }
}
