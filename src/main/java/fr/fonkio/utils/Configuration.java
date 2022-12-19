package fr.fonkio.utils;

import fr.fonkio.json.JSONReader;
import fr.fonkio.json.JSONWriter;
import net.dv8tion.jda.api.entities.Guild;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configuration {

    private final JSONObject jsonObject;
    private final File file;

    public Configuration(String path) throws IOException {
        this.file = new File(path);
        if (this.file.exists()) {
            this.jsonObject = new JSONReader(this.file).toJSONObject();
        } else {
            jsonObject = new JSONObject();
        }
    }

   public JSONObject newServer() {
        JSONObject jo = new JSONObject();
        jo.put(ConfigurationEnum.DOCLIST.getKey(), new JSONArray());
        return jo;
   }

    public String getToken() {
        if(!jsonObject.has(ConfigurationEnum.TOKEN.getKey())) {
            jsonObject.put(ConfigurationEnum.TOKEN.getKey(), ConfigurationEnum.TOKEN.getDefaultValue());
            save();
        }
        return jsonObject.getString(ConfigurationEnum.TOKEN.getKey());
    }

    /**
     * @param guildId Id de la Guild à récupérer
     * @return Le JSONObject du la Guild, il en créé un si il ne trouve pas le serveur dans la config
     */
    private JSONObject getServerConfig(String guildId) {
        if(!jsonObject.has(guildId)) {
            jsonObject.put(guildId, newServer());
            save();
        }
        return jsonObject.getJSONObject(guildId);
    }


    /**
     * Retourne la liste des doc de la guilde
     * @param guildId l'id de la guilde
     * @return
     */
    public Set<String> getGuildDocIdList(String guildId) {
        try {
            JSONArray docArray = getServerConfig(guildId).getJSONArray(ConfigurationEnum.DOCLIST.getKey());
            Set<String> docSet = new HashSet<>();
            for (int i = 0; i < docArray.length(); i++) {
                JSONObject docObject = docArray.getJSONObject(i);
                String id = docObject.getString(ConfigurationEnum.DOCID.getKey());
                docSet.add(id);
            }
            return docSet;
        } catch (JSONException e) {
            return new HashSet<>();
        }
    }

    public Doc getGuildDoc(String guildId, String docId) {
        try {
            JSONArray docArray = getServerConfig(guildId).getJSONArray(ConfigurationEnum.DOCLIST.getKey());
            for (int i = 0; i < docArray.length(); i++) {
                JSONObject docObject = docArray.getJSONObject(i);
                String id = docObject.getString(ConfigurationEnum.DOCID.getKey());
                if (id.equals(docId)) {
                    String title = docObject.getString(ConfigurationEnum.DOCTITLE.getKey());
                    String desc = docObject.getString(ConfigurationEnum.DOCDESC.getKey());
                    String link = docObject.getString(ConfigurationEnum.DOCLINK.getKey());
                    return new Doc(id, title, desc, link);
                }
            }
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean containsGuildDoc(String guildId, String docId) {
        try {
            JSONArray docArray = getServerConfig(guildId).getJSONArray(ConfigurationEnum.DOCLIST.getKey());
            boolean find = false;
            for (int i = 0; i < docArray.length() && !find; i++) {
                JSONObject docObject = docArray.getJSONObject(i);
                String id = docObject.getString(ConfigurationEnum.DOCID.getKey());
                find = find || id.equals(docId);
            }
            return find;
        } catch (JSONException e) {
            return false;
        }
    }

    public void addGuildDocList(String guildId, Doc docToAdd) {
        try {
            JSONArray docArray = getServerConfig(guildId).getJSONArray(ConfigurationEnum.DOCLIST.getKey());
            JSONObject docJsonObject = new JSONObject();
            docJsonObject.put(ConfigurationEnum.DOCID.getKey(), docToAdd.getId());
            docJsonObject.put(ConfigurationEnum.DOCTITLE.getKey(), docToAdd.getTitle());
            docJsonObject.put(ConfigurationEnum.DOCDESC.getKey(), docToAdd.getDesc());
            docJsonObject.put(ConfigurationEnum.DOCLINK.getKey(), docToAdd.getLink());
            docArray.put(docJsonObject);
            save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeGuildDocList(String guildId, Doc docToRemove) {
        try {
            JSONArray docArray = getServerConfig(guildId).getJSONArray(ConfigurationEnum.DOCLIST.getKey());
            boolean find = false;
            for(int i = 0; i < docArray.length() && !find; i++) {
                if (docArray.getJSONObject(i).get(ConfigurationEnum.DOCID.getKey()).equals(docToRemove.getId())) {
                    docArray.remove(i);
                    find = true;
                }
            }
            save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (JSONWriter writer = new JSONWriter(file)) {
            writer.write(this.jsonObject);
            writer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
