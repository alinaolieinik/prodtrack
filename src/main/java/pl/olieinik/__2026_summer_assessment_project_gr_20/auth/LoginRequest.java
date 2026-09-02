package pl.olieinik.__2026_summer_assessment_project_gr_20.auth;

public class LoginRequest {
    private String pin;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
