public class Patient {
  private String nik;
  private String name;
  private String address;
  private String date;

  public Patient() {
    // Empty Constructor
  }

  public Patient(String nik, String name, String address, String date) {
    this.nik = nik;
    this.name = name;
    this.address = address;
    this.date = date;
  }

  public String getNik() {
    return nik;
  }

  public void setNik(String nik) {
    this.nik = nik;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
