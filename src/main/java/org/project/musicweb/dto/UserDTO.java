package org.project.musicweb.dto;

import lombok.Data;
import org.project.musicweb.entity.SubscriptionEntity;
import org.project.musicweb.entity.UserEntity;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long userID;
    private String username;
    private String email;
    private String password;
    private List<String> roles;
    private String profilePic;
    private String signedProfileUrl;
    private String subscriptionType;
    private Date joinDate = new Date();
    private List<SubscriptionEntity> subscriptions;

   // Mapper
    public static UserDTO entityToDTO(UserEntity user, String signedProfileUrl) {
        UserDTO dto = new UserDTO();
        dto.setUserID(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        List<String> roleNames = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        dto.setRoles(roleNames);
        dto.setProfilePic(user.getProfilePic());
        dto.setSignedProfileUrl(signedProfileUrl);
        dto.setJoinDate(user.getJoinDate());
        return dto;
    }
}
