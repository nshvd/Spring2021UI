package pojo;

public class AccountStanding {
    private int id;
    private String code;
    private String name;

    public AccountStanding() {}

    public AccountStanding(int id, String code, String open) {
        this.id = id;
        this.code = code;
        this.name = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AccountStanding{" +
                "id=" + getId() +
                ", code='" + getCode() + '\'' +
                ", open='" + getName() + '\'' +
                '}';
    }
}
