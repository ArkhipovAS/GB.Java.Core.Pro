package server;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {
    private class UserData {
        private String login;
        private String password;
        private String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<UserData> users;

    public SimpleAuthService() {
        users = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            users.add(new UserData("login" + i, "pass" + i, "nick" + i));

        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
//        for (UserData o:users ) {
//            if(o.login.equals(login) && o.password.equals(password)){
//                return o.nickname;
//            }
//        }
        DBAuthService dbas1 = new DBAuthService();
        System.out.println("dbas1.requestSelectDB(login);");
        return dbas1.requestSelectDB(login, password);

//        return null;
    }


    @Override
    public boolean registration(String login, String password, String nickname) {
        for (UserData o:users ) {
            if(o.login.equals(login) || o.nickname.equals(nickname) ){
                return false;
            }
        }

        users.add(new UserData(login, password, nickname));
        DBAuthService dbas = new DBAuthService();
        dbas.requestInsertDB(login, password, nickname);
        return true;
    }

    @Override
    public boolean update(String login, String password, String nickname) {
        DBAuthService dbas = new DBAuthService();
        dbas.requestUpdateDB(login, nickname);
        return true;
    }
}
