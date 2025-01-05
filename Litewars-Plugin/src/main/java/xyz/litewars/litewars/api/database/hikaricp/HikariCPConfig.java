package xyz.litewars.litewars.api.database.hikaricp;

public class HikariCPConfig {
    private String url;
    private String userName;
    private String passWord;
    public HikariCPConfig(String Uri, String userName, String passWord) {
        this.url = Uri;
        this.userName = userName;
        this.passWord = passWord;
    }
    public String getUrl () {
        return this.url;
    }
    public String getUserName () {
        return this.userName;
    }
    public String getPassWord () {
        return this.passWord;
    }
    public void setUrl (String uri) {
        this.url = uri;
    }
    public void setUserName (String userName) {
        this.userName = userName;
    }
    public void setPassWord (String passWord) {
        this.passWord = passWord;
    }
}
