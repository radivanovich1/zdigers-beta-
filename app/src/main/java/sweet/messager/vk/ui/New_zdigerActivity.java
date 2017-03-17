package sweet.messager.vk.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sweet.messager.vk.ApplicationName;
import sweet.messager.vk.R;
import sweet.messager.vk.db.Method;

public class New_zdigerActivity extends AppCompatActivity {
    String path = null;
    ImageView btn_record;
    ImageView btn_play;
    TextView rec_tv,play_tv;
    ImageView btn_stop_play;
    ImageView btn_stop;
    Button btn_save;
    Boolean status_rec =false;
    Boolean status_play =false;
    EditText name;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    String fileName = Environment.getExternalStorageDirectory()+"/1.mp3";
    private static final int FILE_SELECT_CODE = 0;

 String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_zdiger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView bttn_back = (ImageView) findViewById(R.id.button_back_new_zdiger);
        btn_record = (ImageView) findViewById(R.id.button_record);
        btn_play =(ImageView) findViewById(R.id.button_play);
        btn_save = (Button) findViewById(R.id.button_save);
        rec_tv= (TextView)findViewById(R.id.rec_stat);

        name = (EditText) findViewById(R.id.name_z);
        setSupportActionBar(toolbar);
        if (ApplicationName.colors != null) {
            toolbar.setBackgroundColor(ApplicationName.colors.toolBarColor);
        }
        bttn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!status_rec) {
                    recordStart();
                    status_rec = true;
                    btn_record.setImageResource(R.drawable.ic_mic_off_black_48dp);
                    rec_tv.setVisibility(View.VISIBLE);

                }else{
                    recordStop();
                    status_rec = false;
                    btn_record.setImageResource(R.drawable.ic_mic_black_48dp);
                    rec_tv.setVisibility(View.INVISIBLE);
                }
                if (ApplicationName.colors != null) {
                    btn_record.setColorFilter(ApplicationName.colors.toolBarColor);}
                else {btn_record.setColorFilter(ContextCompat.getColor(ApplicationName.getAppContext(),R.color.mainColor));}

            }


        });


        btn_play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!status_play) {
                    playStart();
                    status_play = true;
                    btn_play.setImageResource(R.drawable.ic_pause_circle_outline_black_48dp);


                }else{
                    playStop();

                    status_play = false;
                    btn_play.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                }
                if (ApplicationName.colors != null) {
                    btn_play.setColorFilter(ApplicationName.colors.toolBarColor);}
                else {btn_play.setColorFilter(ContextCompat.getColor(ApplicationName.getAppContext(),R.color.mainColor));}



            }


        });

        btn_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                OperationUpload upload = new OperationUpload();
                upload.execute();
                try {
                    HashMap hh = upload.get();
                    String mid= hh.get("mid").toString();
                    String ur = hh.get("url").toString();
                    Method.addCustomSticker(name.getText().toString(),mid,ur);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }


        });

    }




 public void Play() throws IOException {

     String uri = path;
     MediaPlayer player = new MediaPlayer();
     player.setAudioStreamType(AudioManager.STREAM_MUSIC);
     player.setDataSource(uri);
     player.prepare();
     player.start();


 }

    public void recordStart() {
        try {
            releaseRecorder();

            File outFile = new File(fileName);
            if (outFile.exists()) {
                outFile.delete();
            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setAudioChannels(1);
            mediaRecorder.setOutputFile(fileName);

            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void recordStop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }

    public void playStart() {
        try {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    btn_play.setImageResource(R.drawable.ic_play_zdiget);
                    if (ApplicationName.colors!=null)
                    {
                        btn_play.setColorFilter(ApplicationName.colors.toolBarColor);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }

}
class OperationUpload extends AsyncTask<String,HashMap,HashMap> {
    String token = ApplicationName.access_token;
    Context context;
    String mid ;
    HashMap<String,String> hm= new HashMap<>();
    @Override
    protected HashMap<String, String> doInBackground(String... params)  {

        String charset = "UTF-8";



        File uploadFile1 = new File(Environment.getExternalStorageDirectory() + "/" + "1.mp3");
        URL yahoo = null;

        try {
            yahoo = new URL("https://api.vk.com/method/docs.getUploadServer?access_token="+token+"&type=audio_message&v=5.62");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection yc = null;

        try {
            yc = yahoo.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in1 = null;

        try {
            in1 = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputLine;
        try {
            inputLine = in1.readLine();

            JSONObject obj = null;
            obj = new JSONObject(inputLine.toString());
            String upload_url = null;
            String error;
            upload_url = obj.getJSONObject("response").getString("upload_url");
            String aaa = upload_url;
            String requestURL = upload_url;
            String fileUpl = null;


            sweet.messager.vk.ui.MultipartUtility multipart = new sweet.messager.vk.ui.MultipartUtility(requestURL, charset);

            //multipart.addHeaderField("User-Agent", "CodeJava");

            //multipart.addFormField("description", "Cool Pictures");
            multipart.addFilePart("file", uploadFile1);


            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");
            for (String line : response) {
                System.out.println(line);
                JSONObject obj3 = null;

                obj3 = new JSONObject(line.toString());


                fileUpl = obj3.getString("file");
                String q = fileUpl;


            }


            String ur3 = "https://api.vk.com/method/docs.save?file=" +fileUpl +"&access_token="+token+"&v=5.60";
            URL url1 = new URL(ur3);
            URLConnection yc2 = url1.openConnection();
            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(
                            yc2.getInputStream()));
            String inputLine2;

            inputLine2 = in2.readLine();

            JSONObject obj2 = null;

            obj2 = new JSONObject(inputLine2.toString());


            String id = null;
            String owner_id = null;


            id = obj2.getJSONArray("response").getJSONObject(0).getString("id");
            owner_id = obj2.getJSONArray("response").getJSONObject(0).getString("owner_id");
            String urlll= obj2.getJSONArray("response").getJSONObject(0).getJSONObject("preview").getJSONObject("audio_msg").getString("link_mp3");

            mid = owner_id+"_"+id;



            hm.put("mid", mid);
            hm.put("url",urlll);


        } catch (Exception e) {
            e.printStackTrace();
        }




        return hm;
    }

    @Override
    public void onPostExecute(HashMap values) {

        super.onPostExecute(values);


    }
}

class MultipartUtility {
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;

    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     * @param requestURL
     * @param charset
     * @throws IOException
     */
    public MultipartUtility(String requestURL, String charset)
            throws IOException {
        this.charset = charset;

        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);	// indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        httpConn.setRequestProperty("Test", "Bonjour");
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }

    /**
     * Adds a form field to the request
     * @param name field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     * @param fieldName name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a header field to the request.
     * @param name - name of the header field
     * @param value - value of the header field
     */
    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public List<String> finish() throws IOException {
        List<String> response = new ArrayList<String>();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }

        return response;
    }
}
