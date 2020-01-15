package com.example.test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cipher {

    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        String encryption = cipher.encryption("987654");
        System.err.println(encryption);
        String decryption = cipher.decryption("_6k>o>");
        System.err.println(decryption);
    }

    public String encryption(String input) {
        char[] ch = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= ch.length; ++i) {
            int tmp = ch[i - 1];
            tmp = tmp * i + ch.length;
            tmp %= 96;
            tmp = (tmp + 32) % 128;
            sb.append((char) tmp);
        }
        return sb.toString();
    }

    public String decryption(String passwordStr) {
        List<List<String>> resultList = new ArrayList<>();
        char[] passwordChar = passwordStr.toCharArray();
        for (int i = 1; i <= passwordChar.length; ++i) {
            int password = passwordChar[i - 1];
            List<Integer> origin = new ArrayList<>();
            for (int secondShang = 0; ; secondShang++) {
                if (password + secondShang * 128 - 32 < 96) {
                    int tmp = password + secondShang * 128 - 32;
                    for (int firstShang = 0; ; firstShang++) {
                        if ((tmp + firstShang * 96 - passwordChar.length) % i == 0) {
                            int result = (tmp + firstShang * 96 - passwordChar.length) / i;
                            if ((result >= 0 && result <= 31)) {
                                // 非法字符
                                continue;
                            }
                            if (result > 133) {
                                break;
                            }
                            origin.add(result);
                        }
                    }
                } else {
                    break;
                }
            }
            if (origin.size() == 0) {
                throw new RuntimeException("破解第" + i + "位失败，非正确密文");
            }
            List<String> collect = origin.stream().map(v -> String.valueOf((char) (int) v)).collect(Collectors.toList());
            resultList.add(collect);
        }

        for (int i = 0; i < resultList.size(); i++) {
            System.err.println(resultList.get(i));
        }

        // 从解密出来的每一位的集合中随机选择一个单位拼接成密码，选择顺序按照数字、字母、字符的顺序进行选择
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < resultList.size(); i++) {
            List numList = new ArrayList(); // 数字集合
            List alpList = new ArrayList(); // 字母集合
            List symList = new ArrayList(); // 字符集合

            for (int j = 0; j < resultList.get(i).size(); j++) {
                String str = resultList.get(i).get(j);
                char[] ch = str.toCharArray();
                if (ch[0] >= 48 && ch[0] <= 57) {
                    numList.add(str);
                } else if ((ch[0] >= 65 && ch[0] <= 90) || (ch[0] >= 97 && ch[0] <= 122)) {
                    alpList.add(str);
                } else {
                    symList.add(str);
                }
            }

            if (numList.size() != 0) {
                password.append(numList.get(0));
            } else {
                if (alpList.size() != 0) {
                    password.append(alpList.get(0));
                } else {
                    password.append(symList.get(0));
                }
            }

        }
        return String.valueOf(password);
    }

}
