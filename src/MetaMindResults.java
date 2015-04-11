import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MetaMindResults
{
    private int postive , neutral , negative;
    private String text;

    public MetaMindResults(String url) {
        this.text = url;

        classifyText("XSA06ScCnzjHnYcyBIow8ErOOX9mRWYmESqor2rud0G3kxth4q", 155, text);
    }

    public int getPostive() {
        return postive;
    }

    public int getNeutral() {
        return neutral;
    }

    public int getNegative() {
        return negative;
    }

    @Override
    public String toString()
    {
        return "Postive: " + postive + "\n"
                + "Neutral: " + neutral + "\n"
                + "Negative: " + negative + "\n";
    }

    public void classifyText(String apiKey, int classifierId, String input)
    {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        String jsonResult = null;
        String cmds[] = {
                "curl",
                "-H", "Authorization: Basic "+apiKey,
                "-d", "{\"classifier_id\":"+classifierId+",\"value\":\""+input+"\"}",
                "https://www.metamind.io/language/classify"
        };

        try {
            process = runtime.exec(cmds);
            jsonResult = IOUtils.toString(process.getInputStream(), "UTF8");

            int resultCode = process.waitFor();
            assert resultCode == 0;
        } catch (Throwable cause) {
            // process cause
        }

        try
        {
            final JSONObject obj = new JSONObject(jsonResult);
            final JSONArray geodata = obj.getJSONArray("predictions");
            final int n = geodata.length();
            for (int i = 0; i < n; ++i)
            {
                final JSONObject data = geodata.getJSONObject(i);

                if(data.getString("class_name").equals("neutral"))
                    neutral = (int)(data.getDouble("prob") * 100);
                else if(data.getString("class_name").equals("negative"))
                    negative = (int)(data.getDouble("prob") * 100);
                else
                    postive = (int)(data.getDouble("prob") * 100);
            }
        }catch (Exception e)
        {

        }
    }
}
