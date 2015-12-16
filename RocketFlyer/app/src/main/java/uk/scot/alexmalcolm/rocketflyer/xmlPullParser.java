package uk.scot.alexmalcolm.rocketflyer;

// Alexander Malcolm - S1317460

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.os.AsyncTask;
import android.util.Log;

//this class will grab the data from the stream, parse it and then send it to the UI
public class xmlPullParser extends AsyncTask<String, String, String>
{
    double latitude, longitude;
    String geoName = "haha";
    String currentWeather = "haha";

    public xmlPullParser(double latitudeIn, double longitudeIn)
    {
        latitude = latitudeIn;
        longitude = longitudeIn;
        execute();
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        try //to get the data
        {
            String result;
            // Get the data from the RSS stream as a string
            result = sourceListingString("http://api.geonames.org/findNearby?lat=" + latitude + "&lng=" + longitude +
                    "&fclass=P&fcode=PPLA&fcode=PPL&fcode=PPLC&username=alexmalcolm&style=full");

            Log.e("Debug", "finished grabbing the url stuff, about to parse the data");
            // Do some processing of the data to get the individual parts of the XML stream
            // At some point put this processing into a separate thread of execution

           geoName = parseData(result);

        } catch (IOException ae)
        {
            // Handle error
            //response.setText("Error");
            // Add error info to log for diagnostics
            //errorText.setText(ae.toString());
        }

        mcRssParser rssReader = new mcRssParser();
        try
        {
            rssReader.parseRSSData("http://open.live.bbc.co.uk/weather/feeds/en/" + geoName + "/observations.rss");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        currentWeather = rssReader.getRSSDataItem().getItemTitle();

        return null;
    }

    protected void onProgressUpdate(String... progress)
    {

    }

    // after action has complete - dae stuff to ui
    @Override
    protected void onPostExecute(String file_url)
    {
    }

    // Method to handle the reading of the data from the XML stream
    private static String sourceListingString(String urlString) throws IOException
    {
        String result = "";
        InputStream anInStream = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        // Check that the connection can be opened
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try
        {
            // Open connection
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            // Check that connection is Ok
            if (response == HttpURLConnection.HTTP_OK)
            {
                // Connection is Ok so open a reader
                anInStream = httpConn.getInputStream();
                InputStreamReader in = new InputStreamReader(anInStream);
                BufferedReader bin = new BufferedReader(in);

                // Read in the data from the XML stream
                bin.readLine(); // Throw away the header
                String line = new String();
                while (((line = bin.readLine())) != null)
                {
                    result = result + "\n" + line;
                }
            }
        } catch (Exception ex)
        {
            throw new IOException("Error connecting");
        }

        // Return result as a string for further processing
        return result;

    } // End of sourceListingString

    private String parseData(String dataToParse)
    {
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if (eventType == XmlPullParser.START_TAG)
                {
                    Log.e("Debug", xpp.getName());
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("geonameId"))
                    {
                        //if we find the geoname
                        //save it
                        geoName = xpp.nextText();
                        Log.e("Debug", "geoname = " + geoName);
                    }
                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("geonameId"))
                    {
                        //end of geoname tag
                        Log.e("Debug", "geoname = " + geoName);
                        //put it in a log statement and return
                    }
                }
                // Get the next event
                eventType = xpp.next();

            } // End of while
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("Debug", "Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("Debug", "IO error during parsing");
        }

        Log.e("Debug", "End document");

        return geoName;

    }

}
