package com.mendy.customer;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Mendy
 * @create 2023-07-26 13:46
 */


@Entity
@Table(
        name = "customer",
        uniqueConstraints = { @UniqueConstraint(
                                                 name = "customer_email_unique",
                                                 columnNames = "email") }
)
public class Customer implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"

    )
    private Long id;

    @Column( nullable = false)
    private String name;

    @Column( nullable = false)
    private String email;

    @Column( nullable = false)
    private Integer age;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column( nullable = false)
    private String password;

    public Customer(Long id,
                    String name,
                    String email,
                    String password,
                    Integer age,
                    Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password=password;
        this.age = age;
        this.gender = gender;
    }


    public Customer(String name,
                    String email,
                    String password, Integer age,
                    Gender gender) {
        this.name = name;
        this.email = email;
        this.password=password;
        this.age = age;
        this.gender = gender;
    }


    public Customer() {
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (email != null ? !email.equals(customer.email) : customer.email != null) return false;
        if (age != null ? !age.equals(customer.age) : customer.age != null) return false;
        return gender == customer.gender;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
