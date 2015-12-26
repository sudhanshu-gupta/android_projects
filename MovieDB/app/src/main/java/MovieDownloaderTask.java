import android.os.AsyncTask;

import com.amigo.moviedb.MovieHelper;
import com.amigo.moviedb.MovieInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 19/12/15.
 */
public class MovieDownloaderTask extends AsyncTask<String, Void, ArrayList<MovieInfo>> {

    MovieHelper helper;
    public MovieDownloaderTask() {
        helper = new MovieHelper();
    }

    @Override
    protected ArrayList<MovieInfo> doInBackground(String... params) {
        String url = helper.getPopularMoviesUrl();
        try {
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while((line = br.readLine())!=null)
                buffer.append(line);
            return helper.parseMovies(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
