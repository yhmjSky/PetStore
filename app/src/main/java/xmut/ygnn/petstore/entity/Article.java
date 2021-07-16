package xmut.ygnn.petstore.entity;


import java.io.Serializable;

public class Article implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer id;

        private String title;

        private String author;

        private String img;

        private String des;

        private String content;

        public static long getSerialVersionUID() {
                return serialVersionUID;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getImg() {
                return img;
        }

        public void setImg(String img) {
                this.img = img;
        }

        public String getDes() {
                return des;
        }

        public void setDes(String des) {
                this.des = des;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }
}
