package web.auth;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizedUser {
    private Long tokenId;
    private Long userId;
    private Long clientId;
    private Date createTime;
    private String permissions;

    private String name;
    private String mobile;
}
