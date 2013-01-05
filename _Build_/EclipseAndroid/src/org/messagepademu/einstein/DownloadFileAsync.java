package org.messagepademu.einstein;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.messagepademu.einstein.constants.OtherConstants;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class is currently not used.
 */
class DownloadFileAsync extends AsyncTask<String, String, String> {

	;
    private ProgressDialog mProgressDialog;	
	private final EinsteinActivity einsteinActivity;
	
	DownloadFileAsync(EinsteinActivity einsteinActivity) {
		this.einsteinActivity = einsteinActivity;
	}
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        einsteinActivity.showDialog(OtherConstants.DIALOG_DOWNLOAD_PROGRESS);
    }

    @SuppressLint("SdCardPath")
	@Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(aurl[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();

            int lenghtOfFile = conexion.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream("/sdcard/some_photo_from_gdansk_poland.jpg");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(""+(int)((total*100)/lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {}
        return null;

    }
    
    protected void onProgressUpdate(String... progress) {
         Log.d("ANDRO_ASYNC",progress[0]);
         mProgressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void onPostExecute(String unused) {
    	einsteinActivity.dismissDialog(OtherConstants.DIALOG_DOWNLOAD_PROGRESS);
    }
}
