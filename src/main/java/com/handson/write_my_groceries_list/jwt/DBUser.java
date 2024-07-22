package com.handson.write_my_groceries_list.jwt;


import com.google.common.base.MoreObjects;
import com.handson.write_my_groceries_list.model.Receipt;
import org.springframework.data.domain.Persistable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class DBUser implements Serializable, Persistable<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Access(AccessType.PROPERTY)
    protected Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Receipt> receipts;


    protected DBUser() {}

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; //DBUser.hashPassword(unencryptedPassword);
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Transient
    public static String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
//        return Hashing.sha256().hashString(password, Charset.defaultCharset()).toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", name)
                .toString();
    }


    public static final class UserBuilder {

        private Long id;

        private String name;

        private String password;//https://bcrypt-generator.com/ generate password user+email:javainuse,password:$2y$12$JfmXLQVmTZGpeYVgr6AVhejDGynQ739F4pJE1ZjyCPTvKIHTYb2fi

        private List<Receipt> receipts;


        private UserBuilder() {}

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder receipts(List<Receipt> receipts){
            this.receipts = receipts;
            return this;
        }

        public DBUser build() {
            DBUser user = new DBUser();
            user.setName(name);
            user.setPassword(password);
            user.setId(id);
            user.setReceipts(receipts);
            return user;
        }

    }

}
