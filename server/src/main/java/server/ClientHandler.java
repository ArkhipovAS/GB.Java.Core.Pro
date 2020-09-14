package server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;

    private String nick;
    private String login;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            System.out.println("RemoteSocketAddress:  " + socket.getRemoteSocketAddress());
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            byte [] outData =   ("").getBytes();
            String filePath = "server/history.txt";
            System.out.println("Создание лог файла сервера: " + filePath);
            File file = new File(filePath);
            try (FileOutputStream out = new FileOutputStream( filePath, true )) {
                out.write(outData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(() -> {
                try {
//                    socket.setSoTimeout(3000);

                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        System.out.println("Новая команда от клиента: " + str);
                        if (str.startsWith("/reg ")) {
                            System.out.println("сообщение с просьбой регистрации прошло");
                            String[] token = str.split(" ");
                            boolean b = server
                                    .getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (b) {
                                sendMsg("Регистрация прошла успешно");
                            } else {
                                sendMsg("Логин или ник уже занят");
                            }
                        }
//                        if(str.startsWith("/upd ")){
//                            System.out.println("сообщение с просьбой смены ника прошло");
//                            String[] token = str.split(" ");
//                            boolean b = server
//                                    .getAuthService()
//                                    .update(token[1], token[2], token[3]);
//                            if (b) {
//                                sendMsg("Смена ника прошла успешно");
//                            } else {
//                                sendMsg("Ник уже занят");
//                            }
//
//                        }


                        if (str.equals("/end")) {
                            throw new RuntimeException("Клиент отключился крестиком");

                        }
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);

                            login = token[1];

                            if (newNick != null) {
                                if (!server.isLoginAuthorized(login)) {
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(this);
                                    System.out.println("Клиент " + nick + " прошел аутентификацию");
                                    sendHistoryChat(nick);
                                    break;
                                } else {
                                    sendMsg("С этим логином уже авторизовались");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }


                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }

                            if (str.startsWith("/w ")) {
                                String[] token = str.split(" ", 3);
                                if (token.length == 3) {
                                    server.privateMsg(this, token[1], token[2]);
                                }
                            }

                            if(str.startsWith("/upd ")){
                                System.out.println("сообщение с просьбой смены ника прошло");
                                String[] token = str.split(" ");
                                boolean b = server
                                        .getAuthService()
                                        .update(token[1], token[2], token[3]);
                                if (b) {
                                    server.unsubscribe(this);//upd user list?
                                    nick = token[3];
                                    server.subscribe(this);

                                    sendMsg("Смена ника прошла успешно");
                                } else {
                                    sendMsg("Ник уже занят");
                                }

                            }

                        } else {
                            server.broadcastMsg(nick, str);

                            saveHistoryChat(filePath, str);
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHistoryChat(String filePath, String str) {
        String logChat = nick + " " + str + "\n";
        try {
            Files.write(Paths.get(filePath), logChat.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHistoryChat(String nick) {
        getHistoryChat();
    }

    private void getHistoryChat() {

    }


    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }
}
