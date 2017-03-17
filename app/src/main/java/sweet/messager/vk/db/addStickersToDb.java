package sweet.messager.vk.db;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 26.02.2017.
 */

 public class addStickersToDb extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... params) {
try {

    List<String> inputList = Method.getStickers("all");

    Document doc = Jsoup.connect("http://row.by/mp").get();
    for (Element file : doc.select("td a")) {
        inputList.add(file.text());



    }
    inputList.remove("Parent Directory");
    List<String> outputList = new ArrayList<String>();
    for (String elem : inputList) {
        if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                !outputList.contains(elem)
                ) {
            outputList.add(elem);
        }
    }

    for (String elem : outputList) {
        String url = null;
        URL url2;

            url2 = new URL("http://row.by/mp/"+  URLEncoder.encode(elem, "UTF-8"));
            String url1 = url2.toString();
            url= url1.replace("+","%20");
        Method.addSticker(elem, url,"all");

    }


}
catch (Exception e){


}

            try {

                List<String> inputList = Method.getStickers("brb");

                Document doc = Jsoup.connect("http://row.by/brb").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/brb/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"brb");

                }


            }
            catch (Exception e){


            }

            try {

                List<String> inputList = Method.getStickers("shur");

                Document doc = Jsoup.connect("http://row.by/shur").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/shur/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"shur");

                }


            }
            catch (Exception e){


            }

            try {

                List<String> inputList = Method.getStickers("hovan");

                Document doc = Jsoup.connect("http://row.by/hovan").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/hovan/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"hovan");

                }


            }
            catch (Exception e){


            }

            try {

                List<String> inputList = Method.getStickers("larin");

                Document doc = Jsoup.connect("http://row.by/larin").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/larin/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"larin");

                }


            }
            catch (Exception e){


            }
            try {

                List<String> inputList = Method.getStickers("tix");

                Document doc = Jsoup.connect("http://row.by/tix").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/tix/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"tix");

                }


            }
            catch (Exception e){


            }
            try {

                List<String> inputList = Method.getStickers("druj");

                Document doc = Jsoup.connect("http://row.by/druj").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/druj/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"druj");

                }


            }
            catch (Exception e){


            }

            try {

                List<String> inputList = Method.getStickers("ig");

                Document doc = Jsoup.connect("http://row.by/ig").get();
                for (Element file : doc.select("td a")) {
                    inputList.add(file.text());



                }
                inputList.remove("Parent Directory");
                List<String> outputList = new ArrayList<String>();
                for (String elem : inputList) {
                    if ((inputList.indexOf(elem) == inputList.lastIndexOf(elem)) &&
                            !outputList.contains(elem)
                            ) {
                        outputList.add(elem);
                    }
                }

                for (String elem : outputList) {
                    String url = null;
                    URL url2;

                    url2 = new URL("http://row.by/ig/"+  URLEncoder.encode(elem, "UTF-8"));
                    String url1 = url2.toString();
                    url= url1.replace("+","%20");
                    Method.addSticker(elem, url,"ig");

                }


            }
            catch (Exception e){


            }








            return null;

    }

}
