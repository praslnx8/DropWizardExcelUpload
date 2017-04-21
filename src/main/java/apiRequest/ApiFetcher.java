package apiRequest;

/**
 * Created by prasi on 10/3/16.
 */


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiFetcher
{
    private ApiFetcher(){}

    public static ApiResult makeStringRequest(String urlString, ApiRequestType apiRequestType)
    {
        return makeStringRequest(urlString, apiRequestType, null, new HashMap<String, String>());
    }

    public static ApiResult makeStringRequest(String urlString, Map<String, String> mParams, ApiRequestType apiRequestType) {
        urlString = constructUrl(urlString, mParams);

        return makeStringRequest(urlString, apiRequestType, null, new HashMap<String, String>());
    }

    public static ApiResult makeStringRequest(String urlString, ApiRequestType apiRequestType, String data, String encoding)
    {
        ApiResult apiResult = new ApiResult();
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(encoding != null)
            {
                connection.setRequestProperty("Authorization", "Basic " + encoding);
            }
            connection.setRequestMethod(apiRequestType.name());
            connection.setDoOutput(true);

            // RZP: Simplified the data writing process
            if(data != null) {
                connection.getOutputStream().write(data.getBytes("UTF-8"));
            }

            int status = connection.getResponseCode();
            InputStream content;

            // RZP: You need to check for status code
            if(status >= 400)
            {
                content = connection.getErrorStream();
            }
            else
            {
                content = connection.getInputStream();
            }

            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            in.close();

            String response = stringBuilder.toString();
            apiResult.setIsSuccess(true);
            apiResult.setResult(response);
        } catch (MalformedURLException e)
        {
            apiResult.setIsSuccess(false);
            apiResult.setError("MALFORMED URL EXCEPTION : " + e.getMessage());
        }
        catch (IOException e)
        {
            apiResult.setIsSuccess(false);
            apiResult.setError("IO EXCEPTION : " + e.getMessage());
        }

        return apiResult;
    }

    public static ApiResult makeStringRequest(String urlString, ApiRequestType apiRequestType, String data, Map<String, String> headers)
    {
        ApiResult apiResult = new ApiResult();
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(headers != null)
            {
                for(Map.Entry<String, String> entry : headers.entrySet())
                {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod(apiRequestType.name());
            connection.setDoOutput(true);

            // RZP: Simplified the data writing process
            if(data != null) {
                connection.getOutputStream().write(data.getBytes("UTF-8"));
            }

            int status = connection.getResponseCode();
            InputStream content;

            // RZP: You need to check for status code
            if(status >= 400)
            {
                content = connection.getErrorStream();
            }
            else
            {
                content = connection.getInputStream();
            }

            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            in.close();

            String response = stringBuilder.toString();
            apiResult.setIsSuccess(true);
            apiResult.setResult(response);
        } catch (MalformedURLException e)
        {
            apiResult.setIsSuccess(false);
            apiResult.setError("MALFORMED URL EXCEPTION : " + e.getMessage());
        }
        catch (IOException e)
        {
            apiResult.setIsSuccess(false);
            apiResult.setError("IO EXCEPTION : " + e.getMessage());
        }
        catch (Exception e)
        {
            apiResult.setIsSuccess(false);
            apiResult.setError("EXCEPTION : " + e.getMessage());
        }

        return apiResult;
    }

    private static String constructUrl(String url, Map<String, String> mParams) {
        StringBuilder builder = new StringBuilder();

        if (mParams != null) {
            for (String key : mParams.keySet()) {
                String value = String.valueOf(mParams.get(key));
                if (value != null) {
                    try {
                        value = URLEncoder.encode(String.valueOf(value), "UTF-8");


                        if (builder.length() > 0)
                            builder.append("&");
                        builder.append(key).append("=").append(value);
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }
        }

        if (url.contains("?")) {
            url += "&" + builder.toString();
        } else {
            url += "?" + builder.toString();
        }

        return url;
    }



}
