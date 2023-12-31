package com.example.hjs.src.domain.follower;

import com.example.hjs.src.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Followers")
@IdClass(Follower.followerId.class)
public class Follower extends BaseEntity {

    @Id
    @Column(name = "followingId",  nullable = false)
    private Long followingId;
    @Id
    @Column(name = "followerId",  nullable = false)
    private Long followerId;



    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class followerId implements Serializable{
        @NonNull
        private Long followingId;

        @NonNull
        private Long followerId;
    }

}
