package hub.babos.pkuweb.member.domain;

import lombok.Getter;

@Getter
public enum RoleType {

    USER("USER"), ADMIN("ADMIN");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

    public boolean isNot(String roleType) {
        return !this.name.equals(roleType);
    }
}
