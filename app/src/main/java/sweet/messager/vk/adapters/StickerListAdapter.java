package sweet.messager.vk.adapters;

import android.app.Instrumentation;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import sweet.messager.vk.ApplicationName;
import sweet.messager.vk.R;
import sweet.messager.vk.db.Method;

/**
 * Created by Samsung on 15.02.2017.
 */

public class StickerListAdapter extends BaseAdapter {
    private ArrayList<String> names;
    MediaPlayer mp,mp2;
    LayoutInflater inflater;
    SetData setData;
    private Bundle mBundle;
    int currenti,previ;


    public StickerListAdapter(Context context,ArrayList<String> names)
    {


        setData = new SetData();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.names=names;

    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {


        view = inflater.inflate(R.layout.template_list_zdiger,null);

        setData.name= (TextView)view.findViewById(R.id.Name_zdiger);
        setData.name.setText(names.get(i));
        final ImageView button_play= (ImageView) view.findViewById(R.id.play_button);
        ImageView button_send = (ImageView) view.findViewById(R.id.send_button);
        if (ApplicationName.colors != null) {
            button_play.setColorFilter(ApplicationName.colors.toolBarColor);
            button_send.setColorFilter(ApplicationName.colors.toolBarColor);

        }
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {

                try {
                    Method sql = new Method();
                    HashMap<String, String> values = null;
                    values = sql.getmId(names.get(i));
                    String own_idd = values.get("owner_id");
                    if(own_idd == null){

                        String url = sql.geturl(names.get(i));
                        OperationUpload up = new OperationUpload();
                        up.execute(url);
                        String mid = up.get();
                        Method.addowner(mid,names.get(i));


                        int isChat = Method.getchat();
                        if (isChat == 1) {

                            new Operation1().execute(mid);
                        } else {
                            new Operation().execute(mid);
                        }

                    }
                    else{

                        int isChat = Method.getchat();
                        if (isChat == 1) {

                            new Operation1().execute(own_idd);
                        } else {
                            new Operation().execute(own_idd);
                        }

                    }




                }
                catch (Exception e){

                }


            }
        });

        mp2 =new MediaPlayer();
        button_play.setOnClickListener(new View.OnClickListener() {
            String url = Method.geturl(names.get(i));
            @Override
            public void onClick(View v) {
                currenti = i;
                if(mp2!=null&&mp2.isPlaying()&&currenti==previ)
                {
                    mp2.stop();
                    button_play.setImageResource(R.drawable.ic_play_zdiget);
                    if (ApplicationName.colors!=null)
                    {
                        button_play.setColorFilter(ApplicationName.colors.toolBarColor);
                    }

                }
                else
                {
                    mp2.stop();

                    button_play.setImageResource(R.drawable.ic_pause_zdiger);
                    if (ApplicationName.colors!=null)
                    {
                        button_play.setColorFilter(ApplicationName.colors.toolBarColor);
                    }
                    mp2=null;
                    mp = new MediaPlayer();
                    try {
                        mp.setDataSource(url);
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.prepareAsync();
                        mp2=mp;
                        mp2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp2) {
                                mp2.start();
                            }
                        });
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                    previ =i;

                }
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        button_play.setImageResource(R.drawable.ic_play_zdiget);
                        if (ApplicationName.colors!=null)
                        {
                            button_play.setColorFilter(ApplicationName.colors.toolBarColor);
                            previ =i;
                        }
                    }
                });
            }
        });



        return view;
    }

    public void refreshList(){
        this.notifyDataSetChanged();

    }
    public class SetData{
        TextView name;
    }





}


 class Operation extends AsyncTask<String,String,String> {
     Context context;

     @Override
     protected String doInBackground(String... params) {
         String mid = params[0];
         int id = Method.getid();

         String accessToken = ApplicationName.getAccessToken();
         String url1 = "https://api.vk.com/method/messages.send?user_id=" + id + "&attachment=doc" + mid + "&access_token=" + accessToken + "&v=5.60";
         try {

             URL url = new URL(url1);
             URLConnection yc2 = url.openConnection();
             BufferedReader in2 = new BufferedReader(
                     new InputStreamReader(
                             yc2.getInputStream()));
             String inputLine2;

             inputLine2 = in2.readLine();

             JSONObject obj2 = null;

             obj2 = new JSONObject(inputLine2.toString());


             String resp = null;


             resp = obj2.getString("response");
             if (resp == "error") {


             } else {
                 Instrumentation inst = new Instrumentation();
                 inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

             }


         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
 }

     class Operation1 extends AsyncTask<String,String,String> {
         Context context;

         @Override
         protected String doInBackground(String... params) {
             String mid = params[0];
             int id = Method.getid();
             int idd = Math.abs(id - 2000000000);

             String accessToken = ApplicationName.getAccessToken();
             String url1 = "https://api.vk.com/method/messages.send?chat_id=" + idd + "&attachment=doc" + mid + "&access_token=" + accessToken + "&v=5.60";
             try {

                 URL url = new URL(url1);
                 URLConnection yc2 = url.openConnection();
                 BufferedReader in2 = new BufferedReader(
                         new InputStreamReader(
                                 yc2.getInputStream()));
                 String inputLine2;

                 inputLine2 = in2.readLine();

                 JSONObject obj2 = null;

                 obj2 = new JSONObject(inputLine2.toString());


                 String resp = null;



                 resp = obj2.getString("response");
                 if(resp == "error"){


                 }
                 else {
                     Instrumentation inst = new Instrumentation();
                     inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

                 }


             } catch (Exception e) {
                 e.printStackTrace();
             }
             return null;
         }


     }



class OperationUpload extends AsyncTask<String,String,String> {
    String token = ApplicationName.access_token;
    Context context;
    String mid ;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)  {

        String urll = params[0];
        String charset = "UTF-8";


        URL url = null;

        try {
            url = new URL(urll);


        } catch (Exception e) {
            e.printStackTrace();
        }

        URLConnection connection = null;

        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = null;

        try {
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "1.mp3"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buf = new byte[1024];
        while (true) {
            int len = 0;

            try {
                len = in.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (len == -1) {
                break;
            }

            try {
                fos.write(buf, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

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

             mid = owner_id+"_"+id;


        } catch (Exception e) {
            e.printStackTrace();
        }




        return mid;
    }

    @Override
    public void onPostExecute(String values) {

        super.onPostExecute(values);
        Toast toast = Toast.makeText(ApplicationName.getAppContext(),"Отправлено", Toast.LENGTH_SHORT);
        toast.show();


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