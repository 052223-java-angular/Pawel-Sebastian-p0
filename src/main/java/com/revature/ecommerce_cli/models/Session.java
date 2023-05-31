package com.revature.ecommerce_cli.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Session {
    private String id;
    private String username;
    //private String roleId;

    public void setSession(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        

    
    }
}

