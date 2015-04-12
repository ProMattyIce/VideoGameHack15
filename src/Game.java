import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Game {
    private String title;
    private String platforms;

    private Game(){


    }

    static public ArrayList<String> getPlatforms(String title)
    {
        ArrayList<String> r = new ArrayList<String>();
        String temptitle = title.replaceAll(" ", "+");

        try {
            HttpResponse<JsonNode> response = Unirest.get("https://videogamesrating.p.mashape.com/get.php?count=5&game=" + temptitle)
                    .header("X-Mashape-Key", "xAhp9BsXBMmshNQtWbda8tCgn0QYp1B0Ef0jsn1F39m8wfoUMh")
                    .header("Accept", "application/json")
                    .asJson();


            JSONArray jsonarray = new JSONArray(response.getBody().toString());

            for (int i = 0; i < jsonarray.length(); i++)
            {
                JSONObject e = jsonarray.getJSONObject(i);

                if(e.getString("title").toUpperCase().replaceAll(" ", "").equals(title.toUpperCase().replaceAll(" ","")) || jsonarray.length() ==1)
                {
                    r.add(e.get("platforms").toString().substring(e.get("platforms").toString().indexOf("{\"1\":\"") +
                            "{\"1\":\"".length(), e.get("platforms").toString().length() - 2).replace(",", "").replace(":", "").replace("\"", " ").replace(" 3 ", "").replace("2", "").replace("4", "").replace("5", "").trim().replaceAll("\\s+", " "));

                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return r;
    }
}


