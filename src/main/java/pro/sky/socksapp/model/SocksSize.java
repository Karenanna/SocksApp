package pro.sky.socksapp.model;

public enum SocksSize {
    M35("35"),
    M36("36"),
    M37("37"),
    M38("38"),
    M39("39"),
    M40("40"),
    M41("41"),
    M42("42");
private  final String size;
    SocksSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
