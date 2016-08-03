package tw.com.pcschool.t080301;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);
        MyTask task = new MyTask();
        task.execute("http://www.pcschool.com.tw/2015/images/logo.png");
    }

    class MyTask extends AsyncTask<String, Integer, Bitmap>
    {


        @Override
        protected Bitmap doInBackground(String... params) {
            String strurl = params[0];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte b[] = new byte[64];
            Bitmap bitmap = null;
            try {
                URL url = new URL(strurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                Log.d("IMG", "conected");
                InputStream is = conn.getInputStream();
                double totalLength = conn.getContentLength();
                double sum = 0;
                int readSize = 0;
                while ((readSize = is.read(b)) > 0) {
                    bos.write(b, 0, readSize);
                    sum += readSize;
                }
                byte[] result = bos.toByteArray();
                Log.d("IMG", "to byte array finished array length:" + result.length);
                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }

}

