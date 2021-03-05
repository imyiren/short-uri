package com.imyiren.shorturi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * short_uri
 * @author yiren
 */
@Data
public class ShortUriDO implements Serializable {
    private Long id;

    private String originalUri;

    private String persistence;

    private LocalDateTime expireTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ShortUriDO other = (ShortUriDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOriginalUri() == null ? other.getOriginalUri() == null : this.getOriginalUri().equals(other.getOriginalUri()))
            && (this.getPersistence() == null ? other.getPersistence() == null : this.getPersistence().equals(other.getPersistence()))
            && (this.getExpireTime() == null ? other.getExpireTime() == null : this.getExpireTime().equals(other.getExpireTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOriginalUri() == null) ? 0 : getOriginalUri().hashCode());
        result = prime * result + ((getPersistence() == null) ? 0 : getPersistence().hashCode());
        result = prime * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", originalUri=").append(originalUri);
        sb.append(", persistence=").append(persistence);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}