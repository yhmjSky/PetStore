package xmut.ygnn.petstore.entity;
import java.sql.Blob;
import java.io.Serializable;


public class User implements Serializable {

        private static final long serialVersionUID=1L;
        private Integer id;

        private String openid;

        private String username;

        private String nickname;

        private String password;

        private Integer age;

        private Integer sex;

        private String phone;

        private String email;

        private String address;

        private Integer permission;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public User(String openid, String username,
                String nickname, String password, Integer age,
                Integer sex, String phone, String email, Integer permission) {
        this.openid = openid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.email = email;

        this.permission = permission;
    }



    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}